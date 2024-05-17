package com.kom.filmfolio.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.MovieDetail
import com.kom.filmfolio.databinding.FragmentDetailBinding
import com.kom.filmfolio.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : BottomSheetDialogFragment() {
    private lateinit var currentMovie: MovieDetail

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel {
        parametersOf(arguments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        onClickAction()
    }

    private fun onClickAction() {
        binding.layoutDescription.btnShare.setOnClickListener {
            share(currentMovie)
        }
        binding.layoutDescription.btnAddToList.setOnClickListener {
            addToList()
        }
    }

    private fun addToList() {
        viewModel.addItemToList().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_success_add_to_list),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_error_add_to_list),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun share(movie: MovieDetail) {
        val backdropUrl = movie.backdropPath
        if (backdropUrl.isNotEmpty()) {
            val baseUrlImage = "https://image.tmdb.org/t/p/original"
            val shareIntent: Intent =
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out this `${movie.originalTitle}`! \n ${baseUrlImage + backdropUrl}",
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

    private fun observeData() {
        viewModel.movie?.id?.let {
            viewModel.getMovieDetailById(it).observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.layoutState.root.isVisible = false
                        binding.layoutState.pbLoading.isVisible = false
                        it.payload?.let { movieDetail ->
                            currentMovie = movieDetail
                            setupBind(movieDetail)
                        }
                    },
                    doOnLoading = {
                        binding.layoutState.root.isVisible = true
                        binding.layoutState.pbLoading.isVisible = true
                    },
                )
            }
        }
    }

    private fun setupBind(movie: MovieDetail?) {
        val baseUrl = "https://image.tmdb.org/t/p/w500/"
        movie?.let {
            binding.layoutBanner.ivMovieBackground.load(baseUrl + it.backdropPath) {
                crossfade(true)
            }
            binding.layoutBanner.ivMovieImage.load(baseUrl + it.posterPath) {
                crossfade(true)
            }
            binding.layoutBanner.tvMovieTitle.text = it.title
            binding.layoutBanner.tvDetailMovieInformation.text =
                context?.getString(R.string.text_dates, it.releaseDate, it.voteAverage.toString())
            binding.layoutDescription.tvDetailDescription.text = it.overview
        }
    }

    companion object {
        const val EXTRAS_MOVIE = "EXTRAS_MOVIE"
    }
}
