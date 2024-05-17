package com.kom.filmfolio.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity): Long

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteEntity): Int

    @Query("DELETE FROM FAVOURITES WHERE movie_id = :movieId")
    suspend fun removeFavourite(movieId: Int?): Int

    @Query("DELETE FROM FAVOURITES")
    fun deleteAll()
}