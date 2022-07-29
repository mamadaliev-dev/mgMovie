package uz.madgeeks.mimovie.presentation.tv_show_details

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import nl.joery.animatedbottombar.AnimatedBottomBar
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.formatDate
import uz.madgeeks.mimovie.databinding.FragmentTVShowDetailBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.YoutubeActivity
import uz.madgeeks.mimovie.presentation.adapter.CastAdapter
import uz.madgeeks.mimovie.presentation.adapter.GenreListMiniAdapter
import uz.madgeeks.mimovie.presentation.adapter.MovieTrailerAdapter

@AndroidEntryPoint
class TVShowDetailFragment :
    BaseFragment<FragmentTVShowDetailBinding>(FragmentTVShowDetailBinding::inflate) {
    private val viewModel: TVShowDetailViewModel by viewModels()

    private val adapterTrailer by lazy {
        MovieTrailerAdapter()
    }

    private val adapterCast by lazy {
        CastAdapter()
    }

    private val adapter by lazy {
        GenreListMiniAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreate() {
        binding.apply {
            trailersList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            trailersList.adapter = adapterTrailer

            castList.layoutManager = GridLayoutManager(requireContext(),
                3, GridLayoutManager.VERTICAL, false)
            castList.adapter = adapterCast

            allGenresList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            allGenresList.adapter = adapter
        }

        adapter.setItemClickListener { id, name ->
            val bundle = bundleOf("GENRE_ID" to id, "GENRE_NAME" to name)
            navController.navigate(R.id.action_TVShowDetailFragment_to_genreTVFragment, bundle)
        }

        adapterCast.setItemClickListener {
            val bundle = bundleOf("ACTOR_ID" to it)
            navController.navigate(R.id.action_TVShowDetailFragment_to_actorsFragment, bundle)
        }

        val id = requireArguments().getLong("TV_ID", 0)

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
            viewModel.getTVShowDetailById(id)
            viewModel.getTVTrailerListById(id)
        }

        viewModel.getTVTrailerListById(id)
        viewModel.movieTrailerLiveData.observe(viewLifecycleOwner) {
            it.results.let { item ->
                if (item != null) {
                    adapterTrailer.setTrailers(item)
                }
            }
        }

        viewModel.getCredits(id)
        viewModel.creditsLiveData.observe(viewLifecycleOwner) {
            it.cast?.let { it1 -> adapterCast.setPersons(it1) }
        }

        viewModel.getTVShowDetailById(id)
        viewModel.tvShowDetailsLiveData.observe(viewLifecycleOwner) { result ->
            (activity as AppCompatActivity).supportActionBar?.title = result.original_name
            adapter.setGenres(result.genres)
            binding.apply {
                content.text = result.overview
                name.text = result.original_name
                ratingNumber.text = "${result.vote_average}"
                firstAirDate.text = result.first_air_date.formatDate()
                status.text = result.status
                tagline.text = result.tagline
                voteCount.text = "${result.vote_count} total votes"
                numberOfEpisodes.text = "${result.number_of_episodes}"
                numberOfSeasons.text = "${result.number_of_seasons}"

                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${result.backdrop_path}")
                    .into(binding.image)

                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${result.poster_path}")
                    .into(binding.poster)

                poster.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${result.poster_path}")
                    navController.navigate(R.id.action_TVShowDetailFragment_to_imageViewerFragment,
                        bundle)
                }

                image.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${result.backdrop_path}")
                    navController.navigate(R.id.action_TVShowDetailFragment_to_imageViewerFragment,
                        bundle)
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