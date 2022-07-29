package uz.madgeeks.mimovie.presentation.tv_shows

import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentTVShowsBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.TVShowSearchAdapter
import uz.madgeeks.mimovie.presentation.adapter.TVShowsAdapter

@AndroidEntryPoint
class TVShowsFragment : BaseFragment<FragmentTVShowsBinding>(FragmentTVShowsBinding::inflate) {
    val viewModel: TVShowsViewModel by viewModels()
    val adapter by lazy {
        TVShowsAdapter()
    }

    val adapterPop by lazy {
        TVShowsAdapter()
    }

    val adapterAir by lazy {
        TVShowsAdapter()
    }

    val adapterOn by lazy {
        TVShowsAdapter()
    }

    val adapterSearch by lazy {
        TVShowSearchAdapter()
    }

    override fun onViewCreate() {
        binding.apply {
            tvshowsList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            tvshowsList.adapter = adapter

            populartTVshowsList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            populartTVshowsList.adapter = adapterPop

            onTheAirTVshowsList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            onTheAirTVshowsList.adapter = adapterAir

            airingTVshowsList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            airingTVshowsList.adapter = adapterOn

            searchedTVshowsList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            searchedTVshowsList.adapter = adapterSearch
        }


        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

        adapter.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_navigation_tvshows_to_TVShowDetailFragment, bundle)
        }

        adapterOn.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_navigation_tvshows_to_TVShowDetailFragment, bundle)
        }

        adapterAir.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_navigation_tvshows_to_TVShowDetailFragment, bundle)
        }

        adapterPop.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_navigation_tvshows_to_TVShowDetailFragment, bundle)
        }

        adapterSearch.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_navigation_tvshows_to_TVShowDetailFragment, bundle)
        }

        viewModel.topTVShowsListLiveData.observe(viewLifecycleOwner) {
            it?.let { it1 -> adapter.setTVShows(it1) }
        }

        viewModel.airingTodayTVShowsListLiveData.observe(viewLifecycleOwner) {
            it?.let { it1 -> adapterAir.setTVShows(it1) }
        }

        viewModel.popularTVShowsListLiveData.observe(viewLifecycleOwner) {
            it?.let { it1 -> adapterPop.setTVShows(it1) }
        }

        viewModel.onTheAirTVShowsListLiveData.observe(viewLifecycleOwner) {
            it?.let { it1 -> adapterOn.setTVShows(it1) }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.apply {
                getOnTheAirTVShows()
                getPopularTVShows()
                getAllTopRatedTVShows()
            }
        }

        binding.search.isActivated = true

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.searchedTVshowsList.visibility = View.GONE
                } else {
                    viewModel.getSearchedTVShows(query = query)
                    viewModel.searchedTVShowsListLiveData.observe(viewLifecycleOwner) {
                        if (it.isNullOrEmpty()) {
                            binding.mainLayout.visibility = View.VISIBLE
                            binding.searchedTVshowsList.visibility = View.GONE
                        } else {
                            binding.mainLayout.visibility = View.GONE
                            binding.searchedTVshowsList.visibility = View.VISIBLE
                            adapterSearch.setTVShows(it)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getSearchedTVShows(query = it) }
                viewModel.searchedTVShowsListLiveData.observe(viewLifecycleOwner) {
                    if (it.isNullOrEmpty()) {
                        binding.mainLayout.visibility = View.VISIBLE
                        binding.searchedTVshowsList.visibility = View.GONE
                    } else {
                        binding.mainLayout.visibility = View.GONE
                        binding.searchedTVshowsList.visibility = View.VISIBLE
                        adapterSearch.setTVShows(it)
                    }
                }
                return false
            }
        })

        viewModel.getAllTopRatedTVShows()
        viewModel.getAiringTodayTVShows()
        viewModel.getOnTheAirTVShows()
        viewModel.getPopularTVShows()
    }
}