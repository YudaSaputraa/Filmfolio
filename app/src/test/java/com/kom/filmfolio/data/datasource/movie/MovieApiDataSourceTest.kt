package com.kom.filmfolio.data.datasource.movie

import com.kom.filmfolio.data.source.network.model.movie.MovieResponse
import com.kom.filmfolio.data.source.network.network.FilmfolioApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class MovieApiDataSourceTest {
    @MockK
    lateinit var service: FilmfolioApiService
    private lateinit var dataSource: MovieDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MovieApiDataSource(service)
    }

    @Test
    fun getNowPlayingMovie() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getNowPlayingMovie(any(), any()) } returns mockResponse
            val actualResult = dataSource.getNowPlayingMovie("en-US", 1)
            coVerify { service.getNowPlayingMovie(any(), any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun getPopularMovie() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getPopularMovie(any(), any()) } returns mockResponse
            val actualResult = dataSource.getPopularMovie("en-US", 1)
            coVerify { service.getPopularMovie(any(), any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun getTopRelatedMovie() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getTopRelatedMovie(any(), any()) } returns mockResponse
            val actualResult = dataSource.getTopRelatedMovie("en-US", 1)
            coVerify { service.getTopRelatedMovie(any(), any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun getUpcomingMovie() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getUpcomingMovie(any(), any()) } returns mockResponse
            val actualResult = dataSource.getUpcomingMovie("en-US", 1)
            coVerify { service.getUpcomingMovie(any(), any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
