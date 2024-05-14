package com.kom.filmfolio.data.repository

import com.kom.filmfolio.data.datasource.movie.MovieDataSource
import com.kom.filmfolio.data.mapper.toMovies
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.utils.ResultWrapper
import com.kom.filmfolio.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface MovieRepository {
    fun getNowPlayingMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>>

    fun getPopularMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>>

    fun getTopRelatedMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>>

    fun getUpcomingMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>>
}

class MovieRepositoryImpl(
    private val dataSource: MovieDataSource,
) : MovieRepository {
    override fun getNowPlayingMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow { dataSource.getNowPlayingMovie(language, page).results.toMovies() }
    }

    override fun getPopularMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow { dataSource.getPopularMovie(language, page).results.toMovies() }
    }

    override fun getTopRelatedMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow { dataSource.getTopRelatedMovie(language, page).results.toMovies() }
    }

    override fun getUpcomingMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow { dataSource.getUpcomingMovie(language, page).results.toMovies() }
    }
}
