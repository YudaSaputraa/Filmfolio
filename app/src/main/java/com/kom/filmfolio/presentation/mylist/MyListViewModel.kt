package com.kom.filmfolio.presentation.mylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kom.filmfolio.data.model.Favourite
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
    fun getAllFavorite() =
        myListMovieRepository.getAllMyListMovie().asLiveData(Dispatchers.IO).also {
            it.observeForever { result ->
                Log.d("MyListViewModel", "Received result: $result")
            }
        }

    fun deleteFavorite(item: Favourite) {
        viewModelScope.launch(Dispatchers.IO) {
            myListMovieRepository.deleteMovie(item).collect()
        }
    }
}
