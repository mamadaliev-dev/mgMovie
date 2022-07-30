package uz.madgeeks.mimovie.presentation.trailers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentTrailersBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.YoutubeActivity
import uz.madgeeks.mimovie.presentation.adapter.MovieTrailerAdapter
import uz.madgeeks.mimovie.presentation.adapter.MovieTrailerLimitlessAdapter
import uz.madgeeks.mimovie.presentation.tv_show_details.TVShowDetailViewModel
import uz.madgeeks.mimovie.presentation.tv_shows.TVShowsViewModel

@AndroidEntryPoint
class TrailersFragment : BaseFragment<FragmentTrailersBinding>(FragmentTrailersBinding::inflate) {
    val viewModel: TVShowDetailViewModel by viewModels()
    private val adapterTrailer by lazy {
        MovieTrailerLimitlessAdapter()
    }
    override fun onViewCreate() {
        val id = requireArguments().getLong("TV_ID", 0)

        binding.allTrailersList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.allTrailersList.adapter = adapterTrailer

        viewModel.getTVTrailerListById(id)
        viewModel.movieTrailerLiveData.observe(viewLifecycleOwner) {
            it.results.let { item ->
                if (item != null) {
                    adapterTrailer.setTrailers(item)
                }
            }
        }

        adapterTrailer.setItemClickListener {
            val intent = Intent(requireContext(), YoutubeActivity::class.java)
            intent.putExtra("YOUTUBE_VIDEO_ID", it)
            startActivity(intent)
        }
    }
}