package com.kom.filmfolio.data.repository

import com.kom.filmfolio.data.datasource.movie.MovieDataSource
import com.kom.filmfolio.data.mapper.toMovies
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result = dataSource.getNowPlayingMovie(language, page).results.toMovies()
            emit(ResultWrapper.Success(result))
        }
    }

    override fun getPopularMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result = dataSource.getPopularMovie(language, page).results.toMovies()
            emit(ResultWrapper.Success(result))
        }
    }

    override fun getTopRelatedMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result = dataSource.getTopRelatedMovie(language, page).results.toMovies()
            emit(ResultWrapper.Success(result))
        }
    }

    override fun getUpcomingMovie(
        language: String,
        page: Int,
    ): Flow<ResultWrapper<List<Movie>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val result = dataSource.getUpcomingMovie(language, page).results.toMovies()
            emit(ResultWrapper.Success(result))
        }
    }
}
