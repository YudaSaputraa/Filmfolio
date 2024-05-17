package com.kom.filmfolio.presentation.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kom.filmfolio.R
import com.kom.filmfolio.data.model.Favourite
import com.kom.filmfolio.databinding.ItemMyListMovieBinding

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class MovieListAdapter(private val itemClick: (Favourite) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    class MovieViewHolder(
        private val binding: ItemMyListMovieBinding,
        val itemClick: (Favourite) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Favourite) {
            Log.d("MovieViewHolder", "Binding item: $item")
            val baseUrlImage = "https://image.tmdb.org/t/p/w500"
            binding.ivMyListMovie.load(baseUrlImage + item.movieImage) {
                crossfade(true)
                error(R.drawable.img_error)
            }
            itemView.setOnClickListener { itemClick(item) }
        }
    }

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Favourite>() {
                override fun areItemsTheSame(
                    oldItem: Favourite,
                    newItem: Favourite,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Favourite,
                    newItem: Favourite,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Favourite>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieViewHolder {
        val binding =
            ItemMyListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
    ) {
        (holder.bindView(dataDiffer.currentList[position]))
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size
}
