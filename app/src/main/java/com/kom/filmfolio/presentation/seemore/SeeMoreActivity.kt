package com.kom.filmfolio.presentation.seemore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kom.filmfolio.databinding.ActivitySeeMoreBinding
import com.kom.filmfolio.presentation.seemore.adapter.MoviePagingAdapter
import com.kom.filmfolio.presentation.seemore.adapter.SeeMoreAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeMoreActivity : AppCompatActivity() {
    private val binding: ActivitySeeMoreBinding by lazy {
        ActivitySeeMoreBinding.inflate(layoutInflater)
    }

    private val seeMoreViewModel: SeeMoreViewModel by viewModel()
    private val pagingAdapter =
        MoviePagingAdapter(MoviePagingAdapter.UserComparator) {
            Toast.makeText(this, it.id.toString(), Toast.LENGTH_SHORT).show()
        }

    private val movieNowPlayingAdapter: SeeMoreAdapter by lazy {
        SeeMoreAdapter {
            it.let { item ->
                Toast.makeText(this, item.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getMovieWithPaging()
        bindMovieList()
    }

    private fun getMovieWithPaging() {
        val recyclerView = binding.rvSeeMoreMovies
        recyclerView.adapter = pagingAdapter
        lifecycleScope.launch {
            seeMoreViewModel.flow.collectLatest { pagingData ->
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
