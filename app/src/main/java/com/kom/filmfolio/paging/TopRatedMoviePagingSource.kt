package com.kom.filmfolio.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kom.filmfolio.data.mapper.toMovies
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService
import retrofit2.HttpException

class TopRatedMoviePagingSource(
    private val filmFolioApiService: FilmfolioApiService,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = filmFolioApiService.getTopRatedMovie("en-US", nextPageNumber)
            return LoadResult.Page(
                data = response.results.toMovies(),
                prevKey = null,
                nextKey = if (nextPageNumber == response.totalPages) null else (nextPageNumber + 1),
            )
        } catch (e: Exception) {
            Log.d("Paging", "load: ${e.message}")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}
