package uz.madgeeks.mimovie.presentation.genres_list

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import nl.joery.animatedbottombar.AnimatedBottomBar
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentGenresListBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.GenreListAdapter
import uz.madgeeks.mimovie.presentation.genre.GenreViewModel

@AndroidEntryPoint
class GenresListFragment :
    BaseFragment<FragmentGenresListBinding>(FragmentGenresListBinding::inflate) {
    val viewModel: GenreViewModel by viewModels()
    private val adapter by lazy {
        GenreListAdapter()
    }

    private val adapterTV by lazy {
        GenreListAdapter()
    }

    override fun onViewCreate() {
        binding.allGenresList.layoutManager = GridLayoutManager(requireContext(),
            2, GridLayoutManager.VERTICAL, false)
        binding.allGenresList.adapter = adapter

        binding.allGenresTVList.layoutManager = GridLayoutManager(requireContext(),
            2, GridLayoutManager.VERTICAL, false)
        binding.allGenresTVList.adapter = adapterTV


        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllMoviesGenres()
            viewModel.getAllTVShowGenres()
        }

        adapterTV.setItemClickListener { id, name ->
            val bundle = bundleOf("GENRE_ID" to id, "GENRE_NAME" to name)
            navController.navigate(R.id.action_navigation_genre_to_genreTVFragment,
                bundle)
        }

        adapter.setItemClickListener { id, name ->
            val bundle = bundleOf("GENRE_ID" to id, "GENRE_NAME" to name)
            navController.navigate(R.id.action_navigation_genre_to_genreFragment,
                bundle)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        getGenres()
    }

    private fun getGenres() {
        binding.allGenresTab.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab,
            ) {
                when (newTab.title) {
                    "Movies" -> {
                        binding.allGenresList.visibility = View.VISIBLE
                        binding.allGenresTVList.visibility = View.GONE
                        viewModel.genreListLiveData.observe(viewLifecycleOwner) {
                            adapter.setGenres(it)
                        }
                    }
                    "TV Shows" -> {
                        binding.allGenresList.visibility = View.GONE
                        binding.allGenresTVList.visibility = View.VISIBLE
                        viewModel.genreTVListLiveData.observe(viewLifecycleOwner) {
                            adapterTV.setGenres(it)
                        }
                    }
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {

            }
        })

        viewModel.getAllMoviesGenres()
        viewModel.getAllTVShowGenres()
        viewModel.genreListLiveData.observe(viewLifecycleOwner) {
            adapter.setGenres(it)
        }
    }
}