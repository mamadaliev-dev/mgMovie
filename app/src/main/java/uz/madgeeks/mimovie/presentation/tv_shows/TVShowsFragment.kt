package uz.madgeeks.mimovie.presentation.tv_shows

import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
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

    val adapterSearch by lazy {
        TVShowSearchAdapter()
    }

    override fun onViewCreate() {
        binding.apply {
            tvshowsList.layoutManager = GridLayoutManager(requireContext(),
                2, GridLayoutManager.VERTICAL, false);
            tvshowsList.adapter = adapter

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

        adapterSearch.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_navigation_tvshows_to_TVShowDetailFragment, bundle)
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

        getAllTVShows()
    }

    private fun getAllTVShows() {
        binding.bottomBar.addTab(binding.bottomBar.newTab().setText("TOP RATED"))
        binding.bottomBar.addTab(binding.bottomBar.newTab().setText("POPULAR"))
        binding.bottomBar.addTab(binding.bottomBar.newTab().setText("ON THE AIR"))

        binding.bottomBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        viewModel.topTVShowsListLiveData.observe(viewLifecycleOwner) {
                            it?.let { it1 -> adapter.setTVShows(it1) }
                        }
                    }
                    1 -> {
                        viewModel.popularTVShowsListLiveData.observe(viewLifecycleOwner) {
                            it?.let { it1 -> adapter.setTVShows(it1) }
                        }
                    }
                    2 -> {
                        viewModel.onTheAirTVShowsListLiveData.observe(viewLifecycleOwner) {
                            it?.let { it1 -> adapter.setTVShows(it1) }
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewModel.topTVShowsListLiveData.observe(viewLifecycleOwner) {
            it?.let { it1 -> adapter.setTVShows(it1) }
        }
        viewModel.getAllTopRatedTVShows()
        viewModel.getOnTheAirTVShows()
        viewModel.getPopularTVShows()
    }
}