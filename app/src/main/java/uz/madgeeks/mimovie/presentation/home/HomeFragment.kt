package uz.madgeeks.mimovie.presentation.home

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import nl.joery.animatedbottombar.AnimatedBottomBar
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentHomeBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.HomeMovieLatestAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    val viewModel: HomeViewModel by viewModels()
    val adapter by lazy {
        HomeMovieLatestAdapter()
    }

    override fun onViewCreate() {
        binding.apply {
            allMoviesList.layoutManager = GridLayoutManager(requireContext(),
                2, GridLayoutManager.VERTICAL, false);
            allMoviesList.adapter = adapter
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

        binding.swipeRefresh.setOnRefreshListener {
            with(viewModel) {
                getAllTopRatedMovies()
                getAllNewMovies()
                getAllUpcomingMovies()
                getAllPopularMovies()
            }
        }

        adapter.setItemClickListener {
            val bundle = bundleOf("MOVIE_ID" to it)
            navController.navigate(R.id.action_navigation_home_to_movieDetailFragment, bundle)
        }

        viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
            adapter.setMovies(it)
        }

        viewModel.getAllNewMovies()
        viewModel.moviesListLiveData.observe(this) { result ->
            val imageList = ArrayList<SlideModel>() // Create image list
            val positions = ArrayList<Long>() // Create image list
            result?.forEach { movies ->
                positions.add(movies.id)
                imageList.add(
                    SlideModel(
                        "${BuildConfig.BASE_IMAGE_URL}${movies.backdropPath}",
                        movies.originalTitle
                    )
                )
            }

            binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
            binding.imageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    val bundle = bundleOf("MOVIE_ID" to positions[position])
                    navController.navigate(R.id.action_navigation_home_to_movieDetailFragment,
                        bundle)
                }
            })
        }

        viewModel.getAllPopularMovies()
        viewModel.getAllTopRatedMovies()
        viewModel.getAllUpcomingMovies()

        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab,
            ) {
                when (newTab.title) {
                    "TOP RATED" -> {
                        viewModel.topRatedMovieListLiveData.observe(viewLifecycleOwner) {
                            adapter.setMovies(it)
                        }
                    }
                    "POPULAR" -> {
                        viewModel.popularMovieListLiveData.observe(viewLifecycleOwner) {
                            adapter.setMovies(it)
                        }
                    }
                    "UPCOMING" -> {
                        viewModel.upcomingMovieListLiveData.observe(viewLifecycleOwner) {
                            adapter.setMovies(it)
                        }
                    }
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {

            }
        })
    }
}