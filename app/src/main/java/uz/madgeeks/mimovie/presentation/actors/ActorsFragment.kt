package uz.madgeeks.mimovie.presentation.actors

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.BuildConfig
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.data.formatDate
import uz.madgeeks.mimovie.databinding.FragmentActorsBinding
import uz.madgeeks.mimovie.presentation.BaseFragment


@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentActorsBinding>(FragmentActorsBinding::inflate) {
    private val viewModel: ActorsViewModel by viewModels()

    override fun onViewCreate() {
        getActorDetailById(requireArguments().getLong("ACTOR_ID", 287))
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it == true) binding.main.visibility = View.GONE
            else binding.main.visibility = View.VISIBLE
        }
    }

    private fun getActorDetailById(id: Long) {
        viewModel.getAllFamousPersons(id)
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
    }
}