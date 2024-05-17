package com.kom.filmfolio.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var backdropPath: String,
    var id: Int?,
    var originalLanguage: String,
    var originalTitle: String,
    var overview: String,
    var popularity: Double,
    var posterPath: String,
    var releaseDate: String,
    var title: String,
    var voteAverage: Double,
) : Parcelable
