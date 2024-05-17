package com.kom.filmfolio.data.source.network.network

import android.util.Log
import com.kom.filmfolio.BuildConfig
import com.kom.filmfolio.data.source.network.model.detail.DetailMovieResponse
import com.kom.filmfolio.data.source.network.model.movie.MovieResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface FilmfolioApiService {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovie(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRelatedMovie(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovie(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/{id}")
    suspend fun getDetailMovieById(
        @Path("id") id: Int,
        @Query("language") language: String? = "en-US",
    ): DetailMovieResponse

    @GET("3/genre/movie/list")
    suspend fun getGenresMovie(
        @Query("language") language: String,
    ): MovieResponse

    companion object {
        @JvmStatic
        operator fun invoke(): FilmfolioApiService {
            val logging =
                HttpLoggingInterceptor { message -> Log.d("Http-Logging", "log: $message") }
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val original: Request = chain.request()
                        val requestBuilder: Request.Builder =
                            original.newBuilder()
                                .addHeader("accept", "application/json")
                                .addHeader("Authorization", "Bearer ${BuildConfig.API_HEADER}")
                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .addInterceptor(logging)
                    .build()

            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(FilmfolioApiService::class.java)
        }
    }
}
