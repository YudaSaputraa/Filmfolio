package com.kom.filmfolio.data.mapper

import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity

fun Movie?.toFavouriteEntity() =
    FavouriteEntity(
        movieId = this?.id ?: 0,
        backdropPath = this?.backdropPath.orEmpty(),
        originalLanguage = this?.originalLanguage.orEmpty(),
        originalTitle = this?.originalTitle.orEmpty(),
        overview = this?.overview.orEmpty(),
        posterPath = this?.posterPath.orEmpty(),
        popularity = this?.popularity ?: 0.0,
        releaseDate = this?.releaseDate.orEmpty(),
        title = this?.title.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
    )

fun FavouriteEntity?.toFavourite() =
    Movie(
        id = this?.movieId ?: 0,
        backdropPath = this?.backdropPath.orEmpty(),
        originalLanguage = this?.originalLanguage.orEmpty(),
        originalTitle = this?.originalTitle.orEmpty(),
        overview = this?.overview.orEmpty(),
        posterPath = this?.posterPath.orEmpty(),
        popularity = this?.popularity ?: 0.0,
        releaseDate = this?.releaseDate.orEmpty(),
        title = this?.title.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
    )

fun List<FavouriteEntity?>.toFavouriteList() = this.map { it.toFavourite() }
