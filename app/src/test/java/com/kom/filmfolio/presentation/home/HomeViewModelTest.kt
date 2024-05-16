package com.kom.filmfolio.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.filmfolio.data.repository.MovieRepository
import com.kom.filmfolio.data.repository.UserPrefRepository
import com.kom.filmfolio.tools.MainCoroutineRule
import com.kom.filmfolio.tools.getOrAwaitValue
import com.kom.filmfolio.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var movieRepository: MovieRepository

    @MockK
    lateinit var userPrefRepository: UserPrefRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    movieRepository,
                    userPrefRepository,
                ),
            )
    }

    @Test
    fun getNowPlayingMovie() {
        every { movieRepository.getNowPlayingMovie(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }

        val result = viewModel.getNowPlayingMovie("en-US", 1).getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieRepository.getNowPlayingMovie(any(), any()) }
    }

    @Test
    fun getPopularMovie() {
        every { movieRepository.getPopularMovie(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }

        val result = viewModel.getPopularMovie("en-US", 1).getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieRepository.getPopularMovie(any(), any()) }
    }

    @Test
    fun getTopRelatedMovie() {
        every { movieRepository.getTopRelatedMovie(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }

        val result = viewModel.getTopRelatedMovie("en-US", 1).getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieRepository.getTopRelatedMovie(any(), any()) }
    }

    @Test
    fun getUpcomingMovie() {
        every { movieRepository.getUpcomingMovie(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }

        val result = viewModel.getUpcomingMovie("en-US", 1).getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieRepository.getUpcomingMovie(any(), any()) }
    }

    @Test
    fun setAppIntroShown() {
        every { userPrefRepository.setAppIntroShown(any()) } returns Unit
        val result = viewModel.setAppIntroShown(true)
        assertEquals(Unit, result)
        verify { userPrefRepository.setAppIntroShown(any()) }
    }
}
