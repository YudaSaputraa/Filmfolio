package com.kom.filmfolio.data.source.network.model.movie

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Dates(
    @SerializedName("maximum")
    val maximum: String?,
    @SerializedName("minimum")
    val minimum: String?,
)
