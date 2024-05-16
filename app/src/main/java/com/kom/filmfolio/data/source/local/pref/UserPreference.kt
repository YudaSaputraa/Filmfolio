package com.kom.filmfolio.data.source.local.pref

import android.content.SharedPreferences
import com.kom.filmfolio.utils.SharedPreferenceUtils.set

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface UserPreference {
    fun isAppIntroShown(): Boolean

    fun setAppIntroShown(isShown: Boolean)
}

class UserPreferenceImpl(private val pref: SharedPreferences) : UserPreference {
    override fun isAppIntroShown(): Boolean = pref.getBoolean(KEY_APP_INTRO_SHOWN, false)

    override fun setAppIntroShown(isShown: Boolean) {
        pref[KEY_APP_INTRO_SHOWN] = isShown
    }

    companion object {
        const val PREF_NAME = "filmfolio-pref"
        const val KEY_APP_INTRO_SHOWN = "KEY_APP_INTRO_SHOWN"
    }
}
