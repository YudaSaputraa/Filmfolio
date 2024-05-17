package com.kom.filmfolio.data.datasource.movie

import com.kom.filmfolio.data.source.network.model.detail.DetailMovieResponse
import com.kom.filmfolio.data.source.network.model.movie.MovieResponse
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MovieApiDataSource(
    private val service: FilmfolioApiService,
) : MovieDataSource {
    override suspend fun getNowPlayingMovie(
        language: String,
        page: Int,
    ): MovieResponse {
        return service.getNowPlayingMovie(language, page)
    }

    override suspend fun getPopularMovie(
        language: String,
        page: Int,
    ): MovieResponse {
        return service.getPopularMovie(language, page)
    }

    override suspend fun getTopRelatedMovie(
        language: String,
        page: Int,
    ): MovieResponse {
        return service.getTopRatedMovie(language, page)
    }

    override suspend fun getUpcomingMovie(
        language: String,
        page: Int,
    ): MovieResponse {
        return service.getUpcomingMovie(language, page)
    }

    override suspend fun getDetailMovieById(
        id: Int,
        language: String?,
    ): DetailMovieResponse {
        return service.getDetailMovieById(id = id, language = language)
    }
}
