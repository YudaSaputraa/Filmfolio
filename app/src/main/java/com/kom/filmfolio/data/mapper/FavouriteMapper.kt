package com.kom.filmfolio.data.mapper

import com.kom.filmfolio.data.model.Favourite
import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity

fun Favourite?.toFavouriteEntity() =
    FavouriteEntity(
        id = this?.id,
        movieId = this?.movieId ?: 0,
        movieImage = this?.movieImage.orEmpty(),
    )

fun FavouriteEntity?.toFavourite() =
    Favourite(
        id = this?.id,
        movieId = this?.movieId ?: 0,
        movieImage = this?.movieImage.orEmpty(),
    )

fun List<FavouriteEntity?>.toFavouriteList() = this.map { it.toFavourite() }
