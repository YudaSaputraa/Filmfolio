package com.kom.filmfolio.data.datasource.user

import com.kom.filmfolio.data.source.local.pref.UserPreference

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface UserPrefDataSource {
    fun isAppIntroShown(): Boolean

    fun setAppIntroShown(isShown: Boolean)
}

class UserPrefDataSourceImpl(private val userPreference: UserPreference) : UserPrefDataSource {
    override fun isAppIntroShown(): Boolean {
        return userPreference.isAppIntroShown()
    }

    override fun setAppIntroShown(isShown: Boolean) {
        return userPreference.setAppIntroShown(isShown)
    }
}
