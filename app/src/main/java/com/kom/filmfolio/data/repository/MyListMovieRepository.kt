package com.kom.filmfolio.data.repository

import com.kom.filmfolio.data.datasource.favourite.FavouriteDataSource
import com.kom.filmfolio.data.mapper.toFavouriteEntity
import com.kom.filmfolio.data.mapper.toFavouriteList
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity
import com.kom.filmfolio.utils.ResultWrapper
import com.kom.filmfolio.utils.proceed
import com.kom.filmfolio.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface MyListMovieRepository {
    fun createFavorite(movie: Movie): Flow<ResultWrapper<Boolean>>

    fun deleteMovie(item: Movie): Flow<ResultWrapper<Boolean>>

    fun getAllMyListMovie(): Flow<ResultWrapper<List<Movie>>>

    fun deleteAllMyList(): Flow<ResultWrapper<Unit>>
}

class MyListMovieRepositoryImpl(
    private val favouriteDataSource: FavouriteDataSource,
) : MyListMovieRepository {
    override fun createFavorite(movie: Movie): Flow<ResultWrapper<Boolean>> {
        return movie.id?.let { movieId ->
            proceedFlow {
                val affectedRow =
                    favouriteDataSource.insertFavourite(
                        FavouriteEntity(
                            movieId = movieId,
                            posterPath = movie.posterPath,
                            backdropPath = movie.backdropPath,
                            originalTitle = movie.originalTitle,
                            originalLanguage = movie.originalLanguage,
                            overview = movie.overview,
                            popularity = movie.popularity,
                            releaseDate = movie.releaseDate,
                            title = movie.title,
                            voteAverage = movie.voteAverage,
                        ),
                    )

                delay(1000)
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Movie ID doesn't exist")))
        }
    }

    override fun deleteMovie(item: Movie): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { favouriteDataSource.deleteFavourite(item.toFavouriteEntity()) > 0 }
    }

    override fun getAllMyListMovie(): Flow<ResultWrapper<List<Movie>>> {
        return favouriteDataSource.getAllFavourites()
            .map {
                proceed {
                    it.toFavouriteList()
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }
            .catch {
                emit(ResultWrapper.Error(Exception(it)))
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun deleteAllMyList(): Flow<ResultWrapper<Unit>> {
        return proceedFlow {
            favouriteDataSource.deleteAll()
        }
    }
}
