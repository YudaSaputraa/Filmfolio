package com.kom.filmfolio.presentation.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kom.filmfolio.databinding.FragmentMyListBinding
import com.kom.filmfolio.presentation.common.adapter.MovieListAdapter
import com.kom.filmfolio.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {
    private lateinit var binding: FragmentMyListBinding
    private val myListViewModel: MyListViewModel by viewModel()

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter {
            Toast.makeText(requireContext(), it.id.toString(), Toast.LENGTH_SHORT).show()
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

                    result.payload?.let {
                        adapter.submitData(it)
                    }
                },
            )
        }
    }

    private fun setList() {
        binding.rvSeeMoreMovies.adapter = this@MyListFragment.adapter
    }
}
