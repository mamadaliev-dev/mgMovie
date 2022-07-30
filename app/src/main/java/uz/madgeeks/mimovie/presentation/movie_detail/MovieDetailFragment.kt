package uz.madgeeks.mimovie.presentation.movie_detail

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
import uz.madgeeks.mimovie.data.changeMoneyType
import uz.madgeeks.mimovie.data.formatDate
import uz.madgeeks.mimovie.data.runtimeToHM
import uz.madgeeks.mimovie.databinding.FragmentMovieDetailThirdBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.YoutubeActivity
import uz.madgeeks.mimovie.presentation.adapter.CastAdapter
import uz.madgeeks.mimovie.presentation.adapter.GenreListMiniAdapter
import uz.madgeeks.mimovie.presentation.adapter.MovieTrailerAdapter


@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailThirdBinding>(FragmentMovieDetailThirdBinding::inflate) {
    private val viewModel: MovieDetailViewModel by viewModels()

    private val adapter by lazy {
        GenreListMiniAdapter()
    }

    private val adapterCast by lazy {
        CastAdapter()
    }

    private val adapterTrailer by lazy {
        MovieTrailerAdapter()
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreate() {
        binding.apply {
            allGenresList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)

            allGenresList.adapter = adapter

            castList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

            castList.adapter = adapterCast

            trailersList.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)

            trailersList.adapter = adapterTrailer
        }

        val id = requireArguments().getLong("MOVIE_ID", 0)

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
            viewModel.getMovieDetailById(id)
        }

        viewModel.getMovieTrailerListById(id)
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

        viewModel.getMovieTrailerListById(id)
        viewModel.movieTrailerLiveData.observe(viewLifecycleOwner) {
            it.results.let { item ->
                if (item != null) {
                    adapterTrailer.setTrailers(item)
                }
            }
        }

        binding.btnVideo.setOnClickListener {
            val bundle = bundleOf("MOVIE_ID" to id)
            navController.navigate(R.id.action_movieDetailFragment_to_movieTrailersFragment, bundle)
        }

        binding.btnCast.setOnClickListener {
            val bundle = bundleOf("MOVIE_ID" to id)
            navController.navigate(R.id.action_movieDetailFragment_to_movieCastFragment, bundle)
        }

        viewModel.getMovieDetailById(id)
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner) { result ->
            (activity as AppCompatActivity).supportActionBar?.title = result.originalTitle
            binding.apply {
                content.text = result.overview
                name.text = result.originalTitle
                ratingNumber.text = "${result.voteAverage}"
                runtime.text = result.runtime.runtimeToHM()
                budget.text = "\$${result.budget.toString().changeMoneyType()}"
                status.text = result.status
                revenue.text = "\$${result.revenue.toString().changeMoneyType()}"
                voteCount.text = "${result.voteCount} total votes"

                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${result.backdropPath}")
                    .into(binding.image)

                Glide.with(binding.root.context)
                    .load("${BuildConfig.BASE_IMAGE_URL}${result.posterPath}")
                    .into(binding.poster)

                poster.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${result.posterPath}")
                    navController.navigate(R.id.action_movieDetailFragment_to_imageViewerFragment,
                        bundle)
                }

                image.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${result.backdropPath}")
                    navController.navigate(R.id.action_movieDetailFragment_to_imageViewerFragment,
                        bundle)
                }
            }

            if (result.releaseDate.isNotEmpty()) {
                result.releaseDate.let {
                    binding.releaseDate.text = it.formatDate()
                }
            }

            adapter.setGenres(result.genres)

            adapter.setItemClickListener { id, name ->
                val bundle = bundleOf("GENRE_ID" to id, "GENRE_NAME" to name)
                navController.navigate(R.id.action_movieDetailFragment_to_genreFragment, bundle)
            }

            adapterTrailer.setItemClickListener {
                val intent = Intent(requireContext(), YoutubeActivity::class.java)
                intent.putExtra("YOUTUBE_VIDEO_ID", it)
                startActivity(intent)
            }

            adapterCast.setItemClickListener {
                val bundle = bundleOf("ACTOR_ID" to it)
                navController.navigate(R.id.action_movieDetailFragment_to_actorsFragment, bundle)
            }
        }
    }
}