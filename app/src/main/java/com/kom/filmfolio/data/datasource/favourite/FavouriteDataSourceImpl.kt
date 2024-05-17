package com.kom.filmfolio.data.datasource.favourite

import com.kom.filmfolio.data.source.local.database.dao.FavDao
import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

class FavouriteDataSourceImpl(private val dao: FavDao) : FavouriteDataSource {
    override fun getAllFavourites(): Flow<List<FavouriteEntity>> = dao.getAllFavourites()

    override suspend fun insertFavourite(favourite: FavouriteEntity): Long = dao.insertFavourite(favourite)

    override suspend fun deleteFavourite(favourite: FavouriteEntity): Int = dao.deleteFavourite(favourite)

    override suspend fun deleteAll() = dao.deleteAll()
}