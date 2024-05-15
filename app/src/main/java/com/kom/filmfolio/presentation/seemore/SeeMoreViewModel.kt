package com.kom.filmfolio.presentation.seemore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.filmfolio.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class SeeMoreViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    fun getNowPlayingMovie(
        language: String,
        page: Int,
    ) = movieRepository.getNowPlayingMovie(language, page).asLiveData(Dispatchers.IO)

    fun getPopularMovie(
        language: String,
        page: Int,
    ) = movieRepository.getPopularMovie(language, page).asLiveData(Dispatchers.IO)

    fun getTopRelatedMovie(
        language: String,
        page: Int,
    ) = movieRepository.getTopRelatedMovie(language, page).asLiveData(Dispatchers.IO)

    fun getUpcomingMovie(
        language: String,
        page: Int,
    ) = movieRepository.getUpcomingMovie(language, page).asLiveData(Dispatchers.IO)
}
