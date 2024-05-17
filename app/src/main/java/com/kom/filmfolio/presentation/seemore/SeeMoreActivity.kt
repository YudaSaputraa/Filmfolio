package com.kom.filmfolio.presentation.seemore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kom.filmfolio.R
import com.kom.filmfolio.databinding.ActivitySeeMoreBinding
import com.kom.filmfolio.presentation.detail.DetailFragment
import com.kom.filmfolio.presentation.seemore.adapter.MoviePagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeMoreActivity : AppCompatActivity() {
    private val binding: ActivitySeeMoreBinding by lazy {
        ActivitySeeMoreBinding.inflate(layoutInflater)
    }

    private val seeMoreViewModel: SeeMoreViewModel by viewModel()
    private val pagingAdapter =
        MoviePagingAdapter(MoviePagingAdapter.UserComparator) { item ->
            val bottomSheetFragment =
                DetailFragment().apply {
                    arguments =
                        Bundle().apply {
                            putParcelable(DetailFragment.EXTRAS_MOVIE, item)
                        }
                }
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val movieType = intent.getStringExtra("EXTRA_MOVIE_TYPE")

        getSeeMoreMovie(movieType)
        bindMovieList()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getSeeMoreMovie(movieType: String?) {
        if (movieType == "now_playing") {
            getNowPlayingMovieWithPaging()
        } else if (movieType == "popular") {
            getPopularMovieWithPaging()
        } else if (movieType == "top_related") {
            getTopRelatedMovieWithPaging()
        } else if (movieType == "upcoming") {
            getUpcomingMovieWithPaging()
        }
    }

    private fun getNowPlayingMovieWithPaging() {
        binding.tvTitleSeeMore.text = getString(R.string.text_now_playing)
        val recyclerView = binding.rvSeeMoreMovies
        recyclerView.adapter = pagingAdapter
        lifecycleScope.launch {
            seeMoreViewModel.flowNowPlayingMovie.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun getPopularMovieWithPaging() {
        binding.tvTitleSeeMore.text = getString(R.string.text_popular)
        val recyclerView = binding.rvSeeMoreMovies
        recyclerView.adapter = pagingAdapter
        lifecycleScope.launch {
            seeMoreViewModel.flowPopularMovie.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun getTopRelatedMovieWithPaging() {
        binding.tvTitleSeeMore.text = getString(R.string.text_top_related)
        val recyclerView = binding.rvSeeMoreMovies
        recyclerView.adapter = pagingAdapter
        lifecycleScope.launch {
            seeMoreViewModel.flowTopRelatedMovie.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun getUpcomingMovieWithPaging() {
        binding.tvTitleSeeMore.text = getString(R.string.text_upcoming)
        val recyclerView = binding.rvSeeMoreMovies
        recyclerView.adapter = pagingAdapter
        lifecycleScope.launch {
            seeMoreViewModel.flowUpcomingMovie.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun bindMovieList() {
        binding.rvSeeMoreMovies.apply {
            layoutManager =
                GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = this@SeeMoreActivity.pagingAdapter
        }
    }
}
