package com.kom.filmfolio.presentation.seemore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.databinding.ActivitySeeMoreBinding
import com.kom.filmfolio.presentation.seemore.adapter.SeeMoreAdapter
import com.kom.filmfolio.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeMoreActivity : AppCompatActivity() {
    private val binding: ActivitySeeMoreBinding by lazy {
        ActivitySeeMoreBinding.inflate(layoutInflater)
    }

    private val seeMoreViewModel: SeeMoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getMovieData("en-US", 1)
        bindMovieList()
    }

    private val movieNowPlayingAdapter: SeeMoreAdapter by lazy {
        SeeMoreAdapter {
            it.let { item ->
                Toast.makeText(this, item.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMovieData(
        language: String,
        page: Int,
    ) {
        seeMoreViewModel.getNowPlayingMovie(language, page).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    result.payload?.let {
                        bindMovieNowPlaying(it, language, page)
                    }
                },
                doOnError = {
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                },
                doOnLoading = {
                },
                doOnEmpty = {
                },
            )
        }
    }

    private fun bindMovieNowPlaying(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        movieNowPlayingAdapter.submitData(movies, language, page)
    }

    private fun bindMovieList() {
        binding.rvSeeMoreMovies.apply {
            layoutManager =
                GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = this@SeeMoreActivity.movieNowPlayingAdapter
        }
    }
}
