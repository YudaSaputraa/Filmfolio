package com.kom.filmfolio.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo("movie_id")
    var movieId: Int,
    @ColumnInfo("movie_image")
    var posterPath: String,
    @ColumnInfo("movie_backdrop_img")
    var backdropPath: String,
    @ColumnInfo("original_language")
    var originalLanguage: String,
    @ColumnInfo("original_title")
    var originalTitle: String,
    @ColumnInfo("overview")
    var overview: String,
    @ColumnInfo("popularity")
    var popularity: Double,
    @ColumnInfo("release_date")
    var releaseDate: String,
    @ColumnInfo("title")
    var title: String,
    @ColumnInfo("vote_average")
    var voteAverage: Double,
)
