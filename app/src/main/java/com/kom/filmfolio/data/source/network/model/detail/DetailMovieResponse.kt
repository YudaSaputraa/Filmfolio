package com.kom.filmfolio.data.source.network.model.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DetailMovieResponse(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
)
