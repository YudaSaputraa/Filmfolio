package com.kom.filmfolio.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favourite(
    var id: Int? = null,
    var movieId: Int? = null,
    var movieImage: String,
) : Parcelable
