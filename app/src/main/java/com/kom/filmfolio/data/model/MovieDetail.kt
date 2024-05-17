package com.kom.filmfolio.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val id: Int,
    val originalTitle: String,
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val imdbId: String,
    val voteAverage: Double,
) : Parcelable
