package com.kom.filmfolio.presentation.seemore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kom.filmfolio.data.repository.MovieRepository
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService
import com.kom.filmfolio.paging.NowPlayingMoviePagingSource
import com.kom.filmfolio.paging.PopularMoviePagingSource
import com.kom.filmfolio.paging.TopRelatedMoviePagingSource
import com.kom.filmfolio.paging.UpcomingMoviePagingSource
import kotlinx.coroutines.Dispatchers

class SeeMoreViewModel(
    private val movieRepository: MovieRepository,
    private val filmfolioApiService: FilmfolioApiService,
) : ViewModel() {
    val flowNowPlayingMovie =
        Pager(
            PagingConfig(pageSize = 20),
        ) {
            NowPlayingMoviePagingSource(filmfolioApiService)
        }.flow
            .cachedIn(viewModelScope)

    val flowPopularMovie =
        Pager(
            PagingConfig(pageSize = 20),
        ) {
            PopularMoviePagingSource(filmfolioApiService)
        }.flow
            .cachedIn(viewModelScope)

    val flowTopRelatedMovie =
        Pager(
            PagingConfig(pageSize = 20),
        ) {
            TopRelatedMoviePagingSource(filmfolioApiService)
        }.flow
            .cachedIn(viewModelScope)

    val flowUpcomingMovie =
        Pager(
            PagingConfig(pageSize = 20),
        ) {
            UpcomingMoviePagingSource(filmfolioApiService)
        }.flow
            .cachedIn(viewModelScope)

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
