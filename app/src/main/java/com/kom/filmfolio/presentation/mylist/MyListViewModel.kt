package com.kom.filmfolio.presentation.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.repository.MyListMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MyListViewModel(
    private val myListMovieRepository: MyListMovieRepository,
) : ViewModel() {
    fun getAllFavorite() = myListMovieRepository.getAllMyListMovie().asLiveData(Dispatchers.IO)

    fun deleteFavorite(item: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            myListMovieRepository.deleteMovie(item).collect()
        }
    }
}
