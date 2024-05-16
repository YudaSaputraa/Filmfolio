package com.kom.filmfolio.data.repository

import app.cash.turbine.test
import com.kom.filmfolio.data.datasource.movie.MovieDataSource
import com.kom.filmfolio.data.source.network.model.movie.MovieResponse
import com.kom.filmfolio.data.source.network.model.movie.Result
import com.kom.filmfolio.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class MovieRepositoryImplTest {
    @MockK
    lateinit var datasource: MovieDataSource

    private lateinit var menuRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuRepository = MovieRepositoryImpl(datasource)
    }

    @Test
    fun getNowPlayingMovie() {
        val movieResult =
            Result(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                id = 12345,
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "This is a brief description of the movie.",
                popularity = 67.5,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-05-17",
                title = "Movie Title",
                video = false,
                voteAverage = 8.5,
                voteCount = 2000,
            )

        val movieResult2 =
            Result(
                adult = true,
                backdropPath = "/path/to/another_backdrop.jpg",
                id = 67890,
                originalLanguage = "fr",
                originalTitle = "Titre Original",
                overview = "This is another brief description of a different movie.",
                popularity = 45.3,
                posterPath = "/path/to/another_poster.jpg",
                releaseDate = "2023-12-25",
                title = "Another Movie Title",
                video = true,
                voteAverage = 7.2,
                voteCount = 1500,
            )

        val mockListMenu = listOf(movieResult, movieResult2)
        val mockResponse = mockk<MovieResponse>()
        every { mockResponse.results } returns mockListMenu
        runTest {
            coEvery { datasource.getNowPlayingMovie(any(), any()) } returns mockResponse
            menuRepository.getNowPlayingMovie("en-US", 1).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getNowPlayingMovie(any(), any()) }
            }
        }
    }

    @Test
    fun getPopularMovie() {
        val movieResult =
            Result(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                id = 12345,
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "This is a brief description of the movie.",
                popularity = 67.5,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-05-17",
                title = "Movie Title",
                video = false,
                voteAverage = 8.5,
                voteCount = 2000,
            )

        val movieResult2 =
            Result(
                adult = true,
                backdropPath = "/path/to/another_backdrop.jpg",
                id = 67890,
                originalLanguage = "fr",
                originalTitle = "Titre Original",
                overview = "This is another brief description of a different movie.",
                popularity = 45.3,
                posterPath = "/path/to/another_poster.jpg",
                releaseDate = "2023-12-25",
                title = "Another Movie Title",
                video = true,
                voteAverage = 7.2,
                voteCount = 1500,
            )

        val mockListMenu = listOf(movieResult, movieResult2)
        val mockResponse = mockk<MovieResponse>()
        every { mockResponse.results } returns mockListMenu
        runTest {
            coEvery { datasource.getPopularMovie(any(), any()) } returns mockResponse
            menuRepository.getPopularMovie("en-US", 1).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getPopularMovie(any(), any()) }
            }
        }
    }

    @Test
    fun getTopRelatedMovie() {
        val movieResult =
            Result(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                id = 12345,
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "This is a brief description of the movie.",
                popularity = 67.5,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-05-17",
                title = "Movie Title",
                video = false,
                voteAverage = 8.5,
                voteCount = 2000,
            )

        val movieResult2 =
            Result(
                adult = true,
                backdropPath = "/path/to/another_backdrop.jpg",
                id = 67890,
                originalLanguage = "fr",
                originalTitle = "Titre Original",
                overview = "This is another brief description of a different movie.",
                popularity = 45.3,
                posterPath = "/path/to/another_poster.jpg",
                releaseDate = "2023-12-25",
                title = "Another Movie Title",
                video = true,
                voteAverage = 7.2,
                voteCount = 1500,
            )

        val mockListMenu = listOf(movieResult, movieResult2)
        val mockResponse = mockk<MovieResponse>()
        every { mockResponse.results } returns mockListMenu
        runTest {
            coEvery { datasource.getTopRelatedMovie(any(), any()) } returns mockResponse
            menuRepository.getTopRelatedMovie("en-US", 1).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getTopRelatedMovie(any(), any()) }
            }
        }
    }

    @Test
    fun getUpcomingMovie() {
        val movieResult =
            Result(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                id = 12345,
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "This is a brief description of the movie.",
                popularity = 67.5,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-05-17",
                title = "Movie Title",
                video = false,
                voteAverage = 8.5,
                voteCount = 2000,
            )

        val movieResult2 =
            Result(
                adult = true,
                backdropPath = "/path/to/another_backdrop.jpg",
                id = 67890,
                originalLanguage = "fr",
                originalTitle = "Titre Original",
                overview = "This is another brief description of a different movie.",
                popularity = 45.3,
                posterPath = "/path/to/another_poster.jpg",
                releaseDate = "2023-12-25",
                title = "Another Movie Title",
                video = true,
                voteAverage = 7.2,
                voteCount = 1500,
            )

        val mockListMenu = listOf(movieResult, movieResult2)
        val mockResponse = mockk<MovieResponse>()
        every { mockResponse.results } returns mockListMenu
        runTest {
            coEvery { datasource.getUpcomingMovie(any(), any()) } returns mockResponse
            menuRepository.getUpcomingMovie("en-US", 1).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getUpcomingMovie(any(), any()) }
            }
        }
    }
}
