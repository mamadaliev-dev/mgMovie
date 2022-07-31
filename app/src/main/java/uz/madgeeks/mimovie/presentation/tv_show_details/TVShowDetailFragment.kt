package uz.madgeeks.mimovie.presentation.tv_show_details

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.formatDate
import uz.madgeeks.mimovie.databinding.FragmentTVShowDetailBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.YoutubeActivity
import uz.madgeeks.mimovie.presentation.adapter.CastAdapter
import uz.madgeeks.mimovie.presentation.adapter.GenreListMiniAdapter
import uz.madgeeks.mimovie.presentation.adapter.MovieTrailerAdapter
import uz.madgeeks.mimovie.presentation.adapter.SeasonsAdapter

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

    private val adapterSeason by lazy {
        SeasonsAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreate() {

        val TV_ID = requireArguments().getLong("TV_ID", 0)

        binding.apply {
            trailersList.layoutManager = GridLayoutManager(requireContext(),
                2, GridLayoutManager.VERTICAL, false)
            trailersList.adapter = adapterTrailer

            castList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            castList.adapter = adapterCast

            allGenresList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            allGenresList.adapter = adapter

            seasonsList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            seasonsList.adapter = adapterSeason
        }

        adapter.setItemClickListener { id, name ->
            val bundle = bundleOf("GENRE_ID" to id, "GENRE_NAME" to name)
            navController.navigate(R.id.action_TVShowDetailFragment_to_genreTVFragment, bundle)
        }

        adapterCast.setItemClickListener {
            val bundle = bundleOf("ACTOR_ID" to it)
            navController.navigate(R.id.action_TVShowDetailFragment_to_actorsFragment, bundle)
        }

        adapterSeason.setItemClickListener {
            val bundle = bundleOf("SEASON_NUMBER" to it, "TV_ID" to TV_ID)
            navController.navigate(R.id.action_TVShowDetailFragment_to_seasonDetailsFragment,
                bundle)
        }

        binding.btnVideo.setOnClickListener {
            val bundle = bundleOf("TV_ID" to TV_ID)
            navController.navigate(R.id.action_TVShowDetailFragment_to_trailersFragment, bundle)
        }

        binding.btnCast.setOnClickListener {
            val bundle = bundleOf("TV_ID" to TV_ID)
            navController.navigate(R.id.action_TVShowDetailFragment_to_castFragment, bundle)
        }

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
            viewModel.getTVShowDetailById(TV_ID)
            viewModel.getTVTrailerListById(TV_ID)
        }

        viewModel.getTVTrailerListById(TV_ID)
        viewModel.movieTrailerLiveData.observe(viewLifecycleOwner) {
            it.results.let { item ->
                if (item != null) {
                    adapterTrailer.setTrailers(item)
                }
            }
        }

        viewModel.getCredits(TV_ID)
        viewModel.creditsLiveData.observe(viewLifecycleOwner) {
            it.cast?.let { it1 -> adapterCast.setPersons(it1) }
        }

        viewModel.getTVShowDetailById(TV_ID)
        viewModel.tvShowDetailsLiveData.observe(viewLifecycleOwner) { result ->
            (activity as AppCompatActivity).supportActionBar?.title = result.original_name
            adapter.setGenres(result.genres)
            binding.apply {
                content.text = result.overview
                name.text = result.original_name
                ratingNumber.text = "${result.vote_average}"
                firstAirDate.text = result.first_air_date.formatDate()
                status.text = result.status
                voteCount.text = "${result.vote_count} total votes"
                numberOfEpisodes.text = "${result.number_of_episodes}"
                numberOfSeasons.text = "${result.number_of_seasons}"

                adapterSeason.setSeasons(result.seasons)

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