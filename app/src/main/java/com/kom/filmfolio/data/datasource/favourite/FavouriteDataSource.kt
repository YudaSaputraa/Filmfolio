package com.kom.filmfolio.data.datasource.favourite

import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteDataSource {
    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    suspend fun insertFavourite(favourite: FavouriteEntity): Long

    suspend fun deleteFavourite(favourite: FavouriteEntity): Int

    suspend fun deleteAll()
}
