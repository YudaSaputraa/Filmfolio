package com.kom.filmfolio.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo("movie_id")
    var movieId: Int,
    @ColumnInfo("movie_image")
    var movieImage: String
)
