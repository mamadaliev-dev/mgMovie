package uz.madgeeks.mimovie.presentation.cast_list

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentMovieCastBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.CastLimitlessAdapter
import uz.madgeeks.mimovie.presentation.tv_show_details.TVShowDetailViewModel

@AndroidEntryPoint
class CastFragment : BaseFragment<FragmentMovieCastBinding>(FragmentMovieCastBinding::inflate) {
    private val viewModel: TVShowDetailViewModel by viewModels()
    private val adapter by lazy {
        CastLimitlessAdapter()
    }

    override fun onViewCreate() {
        val id = requireArguments().getLong("TV_ID", 0)

        viewModel.getCredits(id)

        binding.castList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.castList.adapter = adapter

        viewModel.creditsLiveData.observe(viewLifecycleOwner) {
            it.cast?.let { it1 -> adapter.setPersons(it1) }
        }

        adapter.setItemClickListener {
            val bundle = bundleOf("ACTOR_ID" to it)
            navController.navigate(R.id.action_castFragment_to_actorsFragment, bundle)
        }
    }
}