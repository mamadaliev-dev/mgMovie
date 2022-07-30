package uz.madgeeks.mimovie.presentation.trailers

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.databinding.FragmentTrailersBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.YoutubeActivity
import uz.madgeeks.mimovie.presentation.adapter.MovieTrailerLimitlessAdapter
import uz.madgeeks.mimovie.presentation.movie_detail.MovieDetailViewModel

@AndroidEntryPoint
class MovieTrailersFragment :
    BaseFragment<FragmentTrailersBinding>(FragmentTrailersBinding::inflate) {
    val viewModel: MovieDetailViewModel by viewModels()
    private val adapterTrailer by lazy {
        MovieTrailerLimitlessAdapter()
    }

    override fun onViewCreate() {
        val id = requireArguments().getLong("MOVIE_ID", 0)

        binding.allTrailersList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.allTrailersList.adapter = adapterTrailer

        viewModel.getMovieTrailerListById(id)
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