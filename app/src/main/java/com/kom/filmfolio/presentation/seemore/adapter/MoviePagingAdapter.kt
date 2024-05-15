package com.kom.filmfolio.presentation.seemore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.databinding.ItemSeeMoreMovieBinding

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MoviePagingAdapter(
    diffCallback: DiffUtil.ItemCallback<Movie>,
    private val itemClick: (Movie) -> Unit,
) : PagingDataAdapter<Movie, MovieViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieViewHolder {
        val binding =
            ItemSeeMoreMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return MovieViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    object UserComparator : DiffUtil.ItemCallback<Movie>() {
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
            return oldItem == newItem
        }
    }
}

class MovieViewHolder(
    private val binding: ItemSeeMoreMovieBinding,
    private val itemClick: (Movie) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie?) {
        item?.let { movie ->
            val baseUrlImage = "https://image.tmdb.org/t/p/w500"
            binding.ivSeeMoreMovie.load(baseUrlImage + movie.posterPath) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            binding.tvMovieTitle.text = movie.title

            binding.root.setOnClickListener {
                itemClick(movie)
            }
        }
    }
}
