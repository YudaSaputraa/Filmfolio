package com.kom.filmfolio.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
                    result.payload?.let {
                        movieItems = it
                        bindMovieNowPlaying(it, language, page)
                    }
                },
                doOnError = {
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                },
            )
        }
        homeViewModel.getPopularMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    result.payload?.let {
                        bindMoviePopular(it, language, page)
                    }
                },
                doOnError = {
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                },
            )
        }
        homeViewModel.getUpcomingMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    result.payload?.let {
                        bindMovieUpcoming(it, language, page)
                    }
                },
                doOnError = {
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
                },
            )
        }
        homeViewModel.getTopRelatedMovie(language, page).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    result.payload?.let {
                        bindMovieTopRelated(it, language, page)
                    }
                },
                doOnError = {
                    Log.d("Load Data : ", "getMovieData: ${it.exception?.message}")
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
}
