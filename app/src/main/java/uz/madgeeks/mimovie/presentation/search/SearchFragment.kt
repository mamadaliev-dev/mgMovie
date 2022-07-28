package uz.madgeeks.mimovie.presentation.search

import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentSearchBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.HomeMovieAdapter


@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    val viewModel: SearchViewModel by viewModels()
    val adapter by lazy {
        HomeMovieAdapter()
    }

    override fun onViewCreate() {
        binding.searchedList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.searchedList.adapter = adapter

        adapter.setItemClickListener {
            val bundle = bundleOf("MOVIE_ID" to it)
            navController.navigate(R.id.action_navigation_search_to_movieDetailFragment, bundle)
        }

        binding.search.isActivated = true

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.notFoundLayout.visibility = View.VISIBLE
                    binding.searchedList.visibility = View.GONE
                } else {
                    viewModel.getSearchMovies(query = query)
                    viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
                        if (it.isNullOrEmpty()) {
                            binding.notFoundLayout.visibility = View.VISIBLE
                            binding.searchedList.visibility = View.GONE
                        } else {
                            binding.notFoundLayout.visibility = View.GONE
                            binding.searchedList.visibility = View.VISIBLE
                            adapter.setMovies(it)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getSearchMovies(query = it) }
                viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
                    if (it.isNullOrEmpty()) {
                        binding.notFoundLayout.visibility = View.VISIBLE
                        binding.searchedList.visibility = View.GONE
                    } else {
                        binding.notFoundLayout.visibility = View.GONE
                        binding.searchedList.visibility = View.VISIBLE
                        adapter.setMovies(it)
                    }
                }
                return false
            }
        })
    }
}