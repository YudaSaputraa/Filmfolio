package com.kom.filmfolio.data.repository

import com.kom.filmfolio.data.datasource.user.UserPrefDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Written by Komang Yuda Saputra
 * Github : https://github.com/YudaSaputraa
 */
class UserPrefRepositoryImplTest {
    @MockK
    lateinit var datasource: UserPrefDataSource
    private lateinit var userPrefRepository: UserPrefRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPrefRepository = UserPrefRepositoryImpl(datasource)
    }

    @Test
    fun isAppIntroShown() {
        every { datasource.isAppIntroShown() } returns true
        val actualResult = userPrefRepository.isAppIntroShown()
        verify { datasource.isAppIntroShown() }
        assertEquals(true, actualResult)
    }

    @Test
    fun setAppIntroShown() {
        every { datasource.setAppIntroShown(any()) } returns Unit
        userPrefRepository.setAppIntroShown(true)
        verify { datasource.setAppIntroShown(any()) }
    }
}
