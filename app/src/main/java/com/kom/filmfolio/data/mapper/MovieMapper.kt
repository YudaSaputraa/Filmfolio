package com.kom.filmfolio.data.mapper

import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.source.network.model.movie.Result

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
fun Result?.toMovie() =
    Movie(
        backdropPath = this?.backdropPath.orEmpty(),
        id = this?.id ?: 0,
        originalLanguage = this?.originalLanguage.orEmpty(),
        originalTitle = this?.originalTitle.orEmpty(),
        overview = this?.overview.orEmpty(),
        popularity = this?.popularity ?: 0.0,
        posterPath = this?.posterPath.orEmpty(),
        releaseDate = this?.releaseDate.orEmpty(),
        title = this?.title.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
    )

fun Collection<Result>?.toMovies() = this?.map { it.toMovie() } ?: listOf()
