package com.kom.filmfolio.presentation.mylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kom.filmfolio.databinding.FragmentMyListBinding
import com.kom.filmfolio.presentation.common.adapter.MovieListAdapter
import com.kom.filmfolio.presentation.detail.DetailFragment
import com.kom.filmfolio.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {
    private lateinit var binding: FragmentMyListBinding
    private val myListViewModel: MyListViewModel by viewModel()

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter {
            it.let { item ->
                Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()
                val bottomSheetFragment =
                    DetailFragment().apply {
                        arguments =
                            Bundle().apply {
                                putParcelable(DetailFragment.EXTRAS_MOVIE, item)
                            }
                    }
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setList()
        observeData()
    }

    private fun observeData() {
        myListViewModel.getAllFavorite().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvSeeMoreMovies.isVisible = true
                    Log.d("MyListFragment", "Received data: ${result.payload}")
                    result.payload?.let {
                        adapter.submitData(it)
                    }
                },
            )
        }
    }

    private fun setList() {
        binding.rvSeeMoreMovies.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.rvSeeMoreMovies.adapter = this@MyListFragment.adapter
    }
}
