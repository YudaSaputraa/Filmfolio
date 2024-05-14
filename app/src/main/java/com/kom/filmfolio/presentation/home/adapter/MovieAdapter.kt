package com.kom.filmfolio.presentation.home.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.databinding.FragmentHomeBinding
import com.kom.filmfolio.databinding.ItemMovieBinding
import com.kom.filmfolio.databinding.LayoutBannerBinding
import kotlin.random.Random

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MovieAdapter(
    private val itemClick: (Movie) -> Unit,
) : RecyclerView.Adapter<MovieAdapter.ItemMovieViewHolder>() {
    private val language = "en-US"
    private val page = 1
    private lateinit var bannerBinding: LayoutBannerBinding
    private val bannerHandler = Handler(Looper.getMainLooper())
    private var currentMovieIndex = 0
    private lateinit var movies: List<Movie>

    class ItemMovieViewHolder(
        private val binding: ItemMovieBinding,
        private val homeBinding: FragmentHomeBinding,
        private val language: String,
        private val page: Int,
        val itemClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(
            item: Movie,
            language: String,
            page: Int,
        ) {
            with(item) {
                val baseUrlImage = "https://image.tmdb.org/t/p/w500"
                binding.ivMovie.load(baseUrlImage + item.posterPath) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                homeBinding.layoutBanner.ivMovieImg.load(baseUrlImage + item.backdropPath) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                homeBinding.layoutBanner.tvMovieDesc.text = item.overview
                homeBinding.layoutBanner.tvMovieTitle.text = item.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    fun bindMovieNowPlaying(
        movies: List<Movie>,
        language: String,
        page: Int,
        bannerBinding: LayoutBannerBinding,
    ) {
        this.movies = movies
        this.bannerBinding = bannerBinding
        updateBannerContent()
    }

    private fun updateBannerContent() {
        val movie = movies[currentMovieIndex]
        val baseUrlImage = "https://image.tmdb.org/t/p/w500"
        bannerBinding.ivMovieImg.load(baseUrlImage + movie.backdropPath) {
            crossfade(true)
            error(R.mipmap.ic_launcher)
        }
        bannerBinding.tvMovieTitle.text = movie.title
        bannerBinding.tvMovieDesc.text = movie.overview

        currentMovieIndex = Random.nextInt(0, movies.size)
        bannerHandler.postDelayed(::updateBannerContent, 7000)
    }

    private val asyncDataDiffer =
        AsyncListDiffer<Movie>(
            this,
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(
        data: List<Movie>,
        language: String,
        page: Int,
    ) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val homeBinding = FragmentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemMovieViewHolder(binding, homeBinding, language, page, itemClick)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ItemMovieViewHolder,
        position: Int,
    ) {
        holder.bindView(asyncDataDiffer.currentList[position], language, page)
    }
}
