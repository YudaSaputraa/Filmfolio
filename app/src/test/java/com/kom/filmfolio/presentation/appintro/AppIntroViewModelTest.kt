package com.kom.filmfolio.presentation.appintro

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kom.filmfolio.data.repository.UserPrefRepository
import com.kom.filmfolio.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class AppIntroViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userPrefRepository: UserPrefRepository
    private lateinit var viewModel: AppIntroViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                AppIntroViewModel(userPrefRepository),
            )
    }

    @Test
    fun isAppIntroShown() {
        every { userPrefRepository.isAppIntroShown() } returns true
        val result = viewModel.isAppIntroShown()
        assertEquals(true, result)
        verify { userPrefRepository.isAppIntroShown() }
    }
}
