package com.kom.filmfolio.di

import com.kom.filmfolio.data.datasource.movie.MovieApiDataSource
import com.kom.filmfolio.data.datasource.movie.MovieDataSource
import com.kom.filmfolio.data.repository.MovieRepository
import com.kom.filmfolio.data.repository.MovieRepositoryImpl
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService
import com.kom.filmfolio.presentation.home.HomeViewModel
import com.kom.filmfolio.presentation.seemore.SeeMoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.scope.get
import org.koin.dsl.module

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
object AppModules {
    private val networkModule =
        module {
            single<FilmfolioApiService> { FilmfolioApiService.invoke() }
        }

    private val datasource =
        module {
            single<MovieDataSource> { MovieApiDataSource(get()) }
        }

    private val repository =
        module {
            single<MovieRepository> { MovieRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::SeeMoreViewModel)
        }

    val modules =
        listOf(
            networkModule,
            datasource,
            repository,
            viewModelModule,
        )
}
