package com.kom.filmfolio.presentation.appintro

import androidx.lifecycle.ViewModel
import com.kom.filmfolio.data.repository.UserPrefRepository

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class AppIntroViewModel(
    private val userPrefRepository: UserPrefRepository,
) : ViewModel() {
    fun isAppIntroShown() = userPrefRepository.isAppIntroShown()
}
