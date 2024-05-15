package com.kom.filmfolio.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService

class NowPlayingMoviePaging(
    val filmfolioApiService: FilmfolioApiService,
    val query: String
) : PagingSource<Int, Movie>() {


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        TODO("Not yet implemented")
    }

}