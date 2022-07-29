package uz.madgeeks.mimovie.presentation.home

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
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

    val adapterPop by lazy {
        HomeMovieLatestAdapter()
    }

    val adapterUp by lazy {
        HomeMovieLatestAdapter()
    }

    override fun onViewCreate() {
        viewModel.getAllNewMovies()
        viewModel.getAllPopularMovies()
        viewModel.getAllTopRatedMovies()
        viewModel.getAllUpcomingMovies()

        binding.apply {
            topRatedMoviesList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            topRatedMoviesList.adapter = adapter

            populartMoviesList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            populartMoviesList.adapter = adapterPop

            upcomingMoviesList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            upcomingMoviesList.adapter = adapterUp
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

        adapterUp.setItemClickListener {
            val bundle = bundleOf("MOVIE_ID" to it)
            navController.navigate(R.id.action_navigation_home_to_movieDetailFragment, bundle)
        }

        adapterPop.setItemClickListener {
            val bundle = bundleOf("MOVIE_ID" to it)
            navController.navigate(R.id.action_navigation_home_to_movieDetailFragment, bundle)
        }

        viewModel.topRatedMovieListLiveData.observe(viewLifecycleOwner) {
            adapter.setMovies(it)
        }

        viewModel.popularMovieListLiveData.observe(viewLifecycleOwner) {
            adapterPop.setMovies(it)
        }

        viewModel.upcomingMovieListLiveData.observe(viewLifecycleOwner) {
            adapterUp.setMovies(it)
        }

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
    }
}