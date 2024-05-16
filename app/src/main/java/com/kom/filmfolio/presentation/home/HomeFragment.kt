package com.kom.filmfolio.presentation.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.databinding.FragmentHomeBinding
import com.kom.filmfolio.presentation.home.adapter.MovieAdapter
import com.kom.filmfolio.presentation.seemore.SeeMoreActivity
import com.kom.filmfolio.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private var nowPlayingMovieLoaded: List<Movie>? = null
    private var isFirstBanner: Boolean = true

    private var bannerTimer: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())

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

        homeViewModel.setAppIntroShown(true)

        bindMovieList()
        getMovieData("en-US", 1)
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivMoreNowPlaying.setOnClickListener {
            val intent = Intent(requireContext(), SeeMoreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun shareContentIntent(movies: List<Movie>) {
        binding.layoutBanner.icShare.setOnClickListener {
            val backdropUrl = movies.firstOrNull()?.backdropPath
            if (!backdropUrl.isNullOrEmpty()) {
                val baseUrlImage = "https://image.tmdb.org/t/p/original"
                val shareIntent: Intent =
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Check out this movie! \n ${baseUrlImage + backdropUrl}",
                        )
                        type = "text/*"
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                startActivity(Intent.createChooser(shareIntent, null))
            } else {
                Toast.makeText(requireContext(), "No backdrop image available", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun startBannerRotation() {
        bannerTimer =
            object : Runnable {
                override fun run() {
                    updateBannerWithRandomMovie()
                    handler.postDelayed(this, if (isFirstBanner) 0 else 5000)
                }
            }

        handler.postDelayed(bannerTimer!!, 0)
        isFirstBanner = false
    }

    private fun updateBannerWithRandomMovie() {
        val randomMovie = nowPlayingMovieLoaded?.get(Random.nextInt(nowPlayingMovieLoaded!!.size))
        randomMovie?.let {
            updateBanner(randomMovie)
        }
    }

    private fun updateBanner(movie: Movie) {
        binding.apply {
            val baseUrlImage = "https://image.tmdb.org/t/p/original"
            layoutBanner.ivMovieImg.load(baseUrlImage + movie.backdropPath) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }

            layoutBanner.tvMovieTitle.text = movie.title
            layoutBanner.tvMovieDesc.text = movie.overview
        }
    }

    private fun getMovieData(
        language: String,
        page: Int,
    ) {
        homeViewModel.getNowPlayingMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvMovieNowPlaying.isVisible = true
                    binding.shmProgress.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        bindMovieNowPlaying(it, language, page)

                        nowPlayingMovieLoaded = it
                        startBannerRotation()
                        shareContentIntent(it)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmBanner.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = true
                    binding.shmBanner.isVisible = true

                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shmProgress.isVisible = false
                    binding.shmBanner.isVisible = false
                    setMenuTitleConstraint(false)
                },
            )
        }
        homeViewModel.getPopularMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvPopular.isVisible = true
                    binding.shmProgressPopular.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        bindMoviePopular(it, language, page)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvPopular.isVisible = false
                    binding.shmBanner.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvPopular.isVisible = false
                    binding.shmProgressPopular.isVisible = true
                    binding.shmBanner.isVisible = true
                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvPopular.isVisible = false
                    binding.shmProgressPopular.isVisible = false
                    binding.shmBanner.isVisible = false
                    setMenuTitleConstraint(true)
                },
            )
        }
        homeViewModel.getUpcomingMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvUpcoming.isVisible = true
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        bindMovieUpcoming(it, language, page)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvUpcoming.isVisible = false
                    binding.shmProgressUpcoming.isVisible = true
                    binding.shmBanner.isVisible = true
                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvUpcoming.isVisible = false
                    binding.shmProgressUpcoming.isVisible = false
                    binding.shmBanner.isVisible = false
                    setMenuTitleConstraint(false)
                },
            )
        }
        homeViewModel.getTopRelatedMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvTopRelated.isVisible = true

                    binding.shmProgressTopRelated.isVisible = false
                    binding.shmBanner.isVisible = false
                    result.payload?.let {
                        bindMovieTopRelated(it, language, page)
                    }
                    setMenuTitleConstraint(false)
                },
                doOnError = {
                    binding.rvTopRelated.isVisible = false
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                    setMenuTitleConstraint(false)
                },
                doOnLoading = {
                    binding.rvTopRelated.isVisible = false
                    binding.shmProgressTopRelated.isVisible = true
                    setMenuTitleConstraint(true)
                },
                doOnEmpty = {
                    binding.rvTopRelated.isVisible = false
                    binding.shmProgressTopRelated.isVisible = false
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
        movieNowPlayingAdapter.submitData(movies, language, page)
    }

    private fun bindMoviePopular(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        moviePopularAdapter.submitData(movies, language, page)
    }

    private fun bindMovieUpcoming(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
        movieUpcomingAdapter.submitData(movies, language, page)
    }

    private fun bindMovieTopRelated(
        movies: List<Movie>,
        language: String,
        page: Int,
    ) {
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
