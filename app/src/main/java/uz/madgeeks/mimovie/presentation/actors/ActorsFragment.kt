package uz.madgeeks.mimovie.presentation.actors

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import nl.joery.animatedbottombar.AnimatedBottomBar
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.formatDate
import uz.madgeeks.mimovie.databinding.FragmentActorsBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.ActorCreditInMovieAdapter
import uz.madgeeks.mimovie.presentation.adapter.ActorCreditInTVShowsAdapter


@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentActorsBinding>(FragmentActorsBinding::inflate) {
    private val viewModel: ActorsViewModel by viewModels()
    private val adapter by lazy {
        ActorCreditInMovieAdapter()
    }

    private val adapterTV by lazy {
        ActorCreditInTVShowsAdapter()
    }

    override fun onViewCreate() {
        binding.movieCastList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.movieCastList.adapter = adapter

        binding.tvCastList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.tvCastList.adapter = adapterTV

        val id = requireArguments().getLong("ACTOR_ID", 287)

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it == true) binding.main.visibility = View.GONE
            else binding.main.visibility = View.VISIBLE
        }

        adapter.setItemClickListener {
            val bundle = bundleOf("MOVIE_ID" to it)
            navController.navigate(R.id.action_actorsFragment_to_movieDetailFragment, bundle)
        }

        adapterTV.setItemClickListener {
            val bundle = bundleOf("TV_ID" to it)
            navController.navigate(R.id.action_actorsFragment_to_TVShowDetailFragment, bundle)
        }

        viewModel.creditsLiveData.observe(viewLifecycleOwner) {
            adapter.setPersons(it)
        }

        viewModel.tvCreditsLiveData.observe(viewLifecycleOwner) {
            adapterTV.setPersons(it)
        }

        viewModel.actorModelLiveData.observe(viewLifecycleOwner) { result ->
            binding.apply {
                name.text = result.name
                biography.text = result.biography
                popularity.text = result.popularity.toString()
                birthday.text = result.birthday?.formatDate()
                place.text = result.place_of_birth

                Glide.with(requireContext())
                    .load("${BuildConfig.BASE_IMAGE_URL}${result.profilePath}")
                    .placeholder(R.drawable.ic_actor)
                    .into(binding.image)

                image.setOnClickListener {
                    val bundle =
                        bundleOf("IMAGE_URL" to "${BuildConfig.BASE_IMAGE_URL}${result.profilePath}")
                    navController.navigate(R.id.action_actorsFragment_to_imageViewerFragment,
                        bundle)
                }
            }
        }

        viewModel.getAllFamousPersons(id)
        viewModel.getMovieCreditsById(id)
        viewModel.getTVCreditsById(id)

        getTab()
    }

    private fun getTab() {
        binding.actorDetailTab.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab,
            ) {
                when (newTab.title) {
                    "Biography" -> {
                        binding.biography.visibility = View.VISIBLE
                        binding.movieCastList.visibility = View.GONE
                        binding.tvCastList.visibility = View.GONE
                    }
                    "Movies" -> {
                        binding.movieCastList.visibility = View.VISIBLE
                        binding.biography.visibility = View.GONE
                        binding.tvCastList.visibility = View.GONE
                    }
                    "TV Shows" -> {
                        binding.tvCastList.visibility = View.VISIBLE
                        binding.movieCastList.visibility = View.GONE
                        binding.biography.visibility = View.GONE
                    }
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {

            }
        })
    }
}