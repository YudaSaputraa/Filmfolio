package com.kom.filmfolio.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.filmfolio.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class HomeViewModel(
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
