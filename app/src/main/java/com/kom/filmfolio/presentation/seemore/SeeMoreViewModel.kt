package com.kom.filmfolio.presentation.seemore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kom.filmfolio.data.repository.MovieRepository
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService
import com.kom.filmfolio.paging.NowPlayingMoviePagingSource
import com.kom.filmfolio.paging.PopularMoviePagingSource
import com.kom.filmfolio.paging.TopRatedMoviePagingSource
import com.kom.filmfolio.paging.UpcomingMoviePagingSource

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

    val flowTopRatedMovie =
        Pager(
            PagingConfig(pageSize = 20),
        ) {
            TopRatedMoviePagingSource(filmfolioApiService)
        }.flow
            .cachedIn(viewModelScope)

    val flowUpcomingMovie =
        Pager(
            PagingConfig(pageSize = 20),
        ) {
            UpcomingMoviePagingSource(filmfolioApiService)
        }.flow
            .cachedIn(viewModelScope)
}
