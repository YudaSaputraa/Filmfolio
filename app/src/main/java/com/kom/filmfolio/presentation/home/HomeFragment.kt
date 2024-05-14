package com.kom.filmfolio.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.databinding.FragmentHomeBinding
import com.kom.filmfolio.presentation.home.adapter.MovieAdapter
import com.kom.filmfolio.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private var movieItems: List<Movie>? = null

    private val movieNowPlayingAdapter: MovieAdapter by lazy {
        MovieAdapter {
            it.let { item ->
                Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val moviePopularAdapter: MovieAdapter by lazy {
        MovieAdapter {
            it.let { item ->
                Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val movieUpcomingAdapter: MovieAdapter by lazy {
        MovieAdapter {
            it.let { item ->
                Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val movieTopRelatedAdapter: MovieAdapter by lazy {
        MovieAdapter {
            it.let { item ->
                Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        bindMovieList()
//        loadMovieData()
        getMovieData("en-US", 1)
    }

//    private fun loadMovieData() {
//        movieItems?.let { bindMovie(it, "en-US", 1) ?: getMovieData("en-US", 1) }
//    }

    private fun getMovieData(
        language: String,
        page: Int,
    ) {
        homeViewModel.getNowPlayingMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvUpcoming.isVisible = true
                    binding.rvPopular.isVisible = true
                    binding.rvTopRelated.isVisible = true
                    binding.rvMovieNowPlaying.isVisible = true
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        movieItems = it
                        bindMovieNowPlaying(it, language, page)

                        movieUpcomingAdapter.bindMovieNowPlaying(
                            it,
                            language,
                            page,
                            binding.layoutBanner,
                        )
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmBanner.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = true
                    binding.shmProgressPopular.isVisible = true
                    binding.shmProgressTopRelated.isVisible = true
                    binding.shmProgressUpcoming.isVisible = true
                    binding.shmBanner.isVisible = true

                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    setMenuTitleConstraint(false)
                },
            )
        }
        homeViewModel.getPopularMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvUpcoming.isVisible = true
                    binding.rvPopular.isVisible = true
                    binding.rvTopRelated.isVisible = true
                    binding.rvMovieNowPlaying.isVisible = true
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        bindMoviePopular(it, language, page)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmBanner.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = true
                    binding.shmProgressPopular.isVisible = true
                    binding.shmProgressTopRelated.isVisible = true
                    binding.shmProgressUpcoming.isVisible = true
                    binding.shmBanner.isVisible = true
                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    setMenuTitleConstraint(true)
                },
            )
        }
        homeViewModel.getUpcomingMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvUpcoming.isVisible = true
                    binding.rvPopular.isVisible = true
                    binding.rvTopRelated.isVisible = true
                    binding.rvMovieNowPlaying.isVisible = true
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        bindMovieUpcoming(it, language, page)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmBanner.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = true
                    binding.shmProgressPopular.isVisible = true
                    binding.shmProgressTopRelated.isVisible = true
                    binding.shmProgressUpcoming.isVisible = true
                    binding.shmBanner.isVisible = true
                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    setMenuTitleConstraint(false)
                },
            )
        }
        homeViewModel.getTopRelatedMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvUpcoming.isVisible = true
                    binding.rvPopular.isVisible = true
                    binding.rvTopRelated.isVisible = true
                    binding.rvMovieNowPlaying.isVisible = true
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    result.payload?.let {
                        bindMovieTopRelated(it, language, page)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = true
                    binding.shmProgressPopular.isVisible = true
                    binding.shmProgressTopRelated.isVisible = true
                    binding.shmProgressUpcoming.isVisible = true
                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvUpcoming.isVisible = false
                    binding.rvPopular.isVisible = false
                    binding.rvTopRelated.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    setMenuTitleConstraint(false)
                },
            )
        }
    }

    private fun bindMovieNowPlaying(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        this.movieItems = movies
        movieNowPlayingAdapter.submitData(movies, language, page)
    }

    private fun bindMoviePopular(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        this.movieItems = movies
        moviePopularAdapter.submitData(movies, language, page)
    }

    private fun bindMovieUpcoming(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        this.movieItems = movies
        movieUpcomingAdapter.submitData(movies, language, page)
    }

    private fun bindMovieTopRelated(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        this.movieItems = movies
        movieTopRelatedAdapter.submitData(movies, language, page)
    }

    private fun bindMovieList() {
        binding.rvMovieNowPlaying.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.movieNowPlayingAdapter
        }
        binding.rvPopular.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.moviePopularAdapter
        }
        binding.rvUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.movieUpcomingAdapter
        }
        binding.rvTopRelated.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.movieTopRelatedAdapter
        }
    }

    private fun setMenuTitleConstraint(isLoading: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clContent)

        if (isLoading) {
            constraintSet.setVisibility(R.id.layout_banner, ConstraintSet.GONE)
            constraintSet.connect(
                R.id.tv_now_playing_title,
                ConstraintSet.TOP,
                R.id.shm_banner,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.shm_progress,
                ConstraintSet.TOP,
                R.id.tv_now_playing_title,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.tv_popular_title,
                ConstraintSet.TOP,
                R.id.shm_progress,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.shm_progress_popular,
                ConstraintSet.TOP,
                R.id.tv_popular_title,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.tv_upcoming_title,
                ConstraintSet.TOP,
                R.id.shm_progress_popular,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.shm_progress_upcoming,
                ConstraintSet.TOP,
                R.id.tv_upcoming_title,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.tv_top_related_title,
                ConstraintSet.TOP,
                R.id.shm_progress_upcoming,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.shm_progress_top_related,
                ConstraintSet.TOP,
                R.id.tv_top_related_title,
                ConstraintSet.BOTTOM,
            )
        } else {
            constraintSet.setVisibility(R.id.layout_banner, ConstraintSet.VISIBLE)
            constraintSet.connect(
                R.id.tv_now_playing_title,
                ConstraintSet.TOP,
                R.id.layout_banner,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.tv_popular_title,
                ConstraintSet.TOP,
                R.id.rv_movie_now_playing,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.tv_upcoming_title,
                ConstraintSet.TOP,
                R.id.rv_popular,
                ConstraintSet.BOTTOM,
            )
            constraintSet.connect(
                R.id.tv_top_related_title,
                ConstraintSet.TOP,
                R.id.rv_upcoming,
                ConstraintSet.BOTTOM,
            )
        }
        constraintSet.applyTo(binding.clContent)
    }
}
