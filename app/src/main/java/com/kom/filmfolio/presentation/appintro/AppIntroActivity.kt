package com.kom.filmfolio.presentation.appintro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import com.kom.filmfolio.R
import com.kom.filmfolio.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppIntroActivity : AppIntro2() {
    private val appIntroViewModel: AppIntroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.layout_intro_welcome),
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.layout_intro_get_started),
        )
        setTransformer(
            AppIntroPageTransformerType.Parallax(
                titleParallaxFactor = 1.0,
                imageParallaxFactor = -1.0,
                descriptionParallaxFactor = 2.0,
            ),
        )

        isIndicatorEnabled = true

        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.md_theme_primary),
            unselectedIndicatorColor = getColor(R.color.darkGrey),
        )
        if (appIntroViewModel.isAppIntroShown()) {
            navigateToHome()
        }
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        navigateToHome()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        navigateToHome()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
