package com.kom.filmfolio.presentation.seemore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.Movie
import com.kom.filmfolio.databinding.ItemSeeMoreMovieBinding

class SeeMoreAdapter(
    private val itemClick: (Movie) -> Unit,
) : RecyclerView.Adapter<SeeMoreAdapter.ItemMovieViewHolder>() {
    private val language = "en-US"
    private val page = 1

    class ItemMovieViewHolder(
        private val binding: ItemSeeMoreMovieBinding,
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
                binding.ivSeeMoreMovie.load(baseUrlImage + item.posterPath) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                binding.tvMovieTitle.text = item.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }
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
        val binding = ItemSeeMoreMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemMovieViewHolder(binding, language, page, itemClick)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ItemMovieViewHolder,
        position: Int,
    ) {
        holder.bindView(asyncDataDiffer.currentList[position], language, page)
    }
}
