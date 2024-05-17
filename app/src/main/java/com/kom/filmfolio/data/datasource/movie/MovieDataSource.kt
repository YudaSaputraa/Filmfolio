package com.kom.filmfolio.data.datasource.movie

import com.kom.filmfolio.data.source.network.model.detail.DetailMovieResponse
import com.kom.filmfolio.data.source.network.model.movie.MovieResponse

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface MovieDataSource {
    suspend fun getNowPlayingMovie(
        language: String,
        page: Int,
    ): MovieResponse

    suspend fun getPopularMovie(
        language: String,
        page: Int,
    ): MovieResponse

    suspend fun getTopRelatedMovie(
        language: String,
        page: Int,
    ): MovieResponse

    suspend fun getUpcomingMovie(
        language: String,
        page: Int,
    ): MovieResponse

    suspend fun getDetailMovieById(
        id: Int,
        language: String?,
    ): DetailMovieResponse
}
