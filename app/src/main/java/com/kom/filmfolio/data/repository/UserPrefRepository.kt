package com.kom.filmfolio.data.repository

import com.kom.filmfolio.data.datasource.user.UserPrefDataSource

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface UserPrefRepository {
    fun isAppIntroShown(): Boolean

    fun setAppIntroShown(isShown: Boolean)
}

class UserPrefRepositoryImpl(private val dataSource: UserPrefDataSource) : UserPrefRepository {
    override fun isAppIntroShown(): Boolean {
        return dataSource.isAppIntroShown()
    }

    override fun setAppIntroShown(isShown: Boolean) {
        return dataSource.setAppIntroShown(isShown)
    }
}
