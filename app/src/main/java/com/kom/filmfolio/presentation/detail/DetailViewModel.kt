package com.kom.filmfolio.presentation.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val extras: Bundle?,
    private val detailRepository: MovieRepository,
) : ViewModel() {
    val movie = extras?.getParcelable<Movie>(DetailFragment.EXTRAS_MOVIE)

    fun getMovieDetailById(id: Int) = detailRepository.getDetailMovieById(id).asLiveData(Dispatchers.IO)
}
