package uz.madgeeks.mimovie.presentation.season_details

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.formatDate
import uz.madgeeks.mimovie.databinding.FragmentSeasonDetailsBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.EpisodesAdapter

@AndroidEntryPoint
class SeasonDetailsFragment :
    BaseFragment<FragmentSeasonDetailsBinding>(FragmentSeasonDetailsBinding::inflate) {
    private val viewModel: SeasonDetailViewModel by viewModels()
    private val adapter by lazy {
        EpisodesAdapter()
    }

    override fun onViewCreate() {
        val TV_ID = requireArguments().getLong("TV_ID", 0)
        val SEASON_NUMBER = requireArguments().getInt("SEASON_NUMBER", 0)

        binding.allEpisodesList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.allEpisodesList.adapter = adapter

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
            if (it == true) {
                binding.main.visibility = View.GONE
            } else {
                binding.main.visibility = View.VISIBLE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getTVShowDetailById(TV_ID, SEASON_NUMBER)
        }

        viewModel.seasonDetailsLiveData.observe(viewLifecycleOwner) { data->
            (activity as AppCompatActivity).supportActionBar?.title = data.name
            binding.apply {
                name.text = data.name
                airDate.text = data.air_date.formatDate()
                overview.text = data.overview
                Glide.with(root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.episodes[0].still_path}")
                    .into(image)

                Glide.with(root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${data.poster_path}")
                    .into(poster)

                poster.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${data.poster_path}")
                    navController.navigate(R.id.action_seasonDetailsFragment_to_imageViewerFragment,
                        bundle)
                }

                image.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${data.episodes[0].still_path}")
                    navController.navigate(R.id.action_seasonDetailsFragment_to_imageViewerFragment,
                        bundle)
                }
            }
            adapter.setEpisodes(data.episodes)
        }

        viewModel.getTVShowDetailById(TV_ID, SEASON_NUMBER)
    }
}