package com.kom.filmfolio.data.datasource.user

import com.kom.filmfolio.data.source.local.pref.UserPreference
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
class UserPrefDataSourceImplTest {
    @MockK
    lateinit var userPreference: UserPreference
    private lateinit var userPrefDataSource: UserPrefDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPrefDataSource = UserPrefDataSourceImpl(userPreference)
    }

    @Test
    fun isAppIntroShown() {
        every { userPrefDataSource.isAppIntroShown() } returns true
        val actualResult = userPreference.isAppIntroShown()
        verify { userPrefDataSource.isAppIntroShown() }
        assertEquals(true, actualResult)
    }

    @Test
    fun setAppIntroShown() {
        every { userPrefDataSource.setAppIntroShown(any()) } returns Unit
        val actualResult = userPreference.setAppIntroShown(true)
        verify { userPrefDataSource.setAppIntroShown(any()) }
        assertEquals(Unit, actualResult)
    }
}
