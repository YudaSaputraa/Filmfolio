package com.kom.filmfolio.di

import android.content.SharedPreferences
import android.os.Bundle
import com.kom.filmfolio.data.datasource.favourite.FavouriteDataSource
import com.kom.filmfolio.data.datasource.favourite.FavouriteDataSourceImpl
import com.kom.filmfolio.data.datasource.movie.MovieApiDataSource
import com.kom.filmfolio.data.datasource.movie.MovieDataSource
import com.kom.filmfolio.data.datasource.user.UserPrefDataSource
import com.kom.filmfolio.data.datasource.user.UserPrefDataSourceImpl
import com.kom.filmfolio.data.repository.MovieRepository
import com.kom.filmfolio.data.repository.MovieRepositoryImpl
import com.kom.filmfolio.data.repository.MyListMovieRepository
import com.kom.filmfolio.data.repository.MyListMovieRepositoryImpl
import com.kom.filmfolio.data.repository.UserPrefRepository
import com.kom.filmfolio.data.repository.UserPrefRepositoryImpl
import com.kom.filmfolio.data.source.local.database.AppDatabase
import com.kom.filmfolio.data.source.local.database.dao.FavDao
import com.kom.filmfolio.data.source.local.pref.UserPreference
import com.kom.filmfolio.data.source.local.pref.UserPreferenceImpl
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService
import com.kom.filmfolio.presentation.appintro.AppIntroViewModel
import com.kom.filmfolio.presentation.detail.DetailViewModel
import com.kom.filmfolio.presentation.home.HomeViewModel
import com.kom.filmfolio.presentation.mylist.MyListViewModel
import com.kom.filmfolio.presentation.seemore.SeeMoreViewModel
import com.kom.filmfolio.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
            single<UserPrefDataSource> { UserPrefDataSourceImpl(get()) }
            single<FavouriteDataSource> { FavouriteDataSourceImpl(get()) }
        }

    private val localModule =
        module {
            single<UserPreference> { UserPreferenceImpl(get()) }
            single<FavDao> { get<AppDatabase>().appDao() }
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
        }

    private val repository =
        module {
            single<MovieRepository> { MovieRepositoryImpl(get()) }
            single<UserPrefRepository> { UserPrefRepositoryImpl(get()) }
            single<MyListMovieRepository> { MyListMovieRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::SeeMoreViewModel)
            viewModel { (extras: Bundle?) ->
                DetailViewModel(extras, get(), get())
            }
            viewModelOf(::AppIntroViewModel)
            viewModelOf(::MyListViewModel)
        }

    val modules =
        listOf(
            networkModule,
            datasource,
            repository,
            localModule,
            viewModelModule,
        )
}
