package com.kom.filmfolio.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.repository.MovieRepository
import com.kom.filmfolio.data.repository.MyListMovieRepository
import com.kom.filmfolio.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val extras: Bundle?,
    private val detailRepository: MovieRepository,
    private val myListMovieRepository: MyListMovieRepository,
) : ViewModel() {
    val movie = extras?.getParcelable<Movie>(DetailFragment.EXTRAS_MOVIE)

    fun getMovieDetailById(id: Int) = detailRepository.getDetailMovieById(id).asLiveData(Dispatchers.IO)

    fun addItemToList(): LiveData<ResultWrapper<Boolean>> {
        return movie?.let {
            myListMovieRepository.createFavorite(it).asLiveData(Dispatchers.IO)
        } ?: liveData {
            emit(ResultWrapper.Error(IllegalStateException("Movie not found!")))
        }
    }
}
