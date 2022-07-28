package uz.madgeeks.mimovie.presentation.actors_list

import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import nl.joery.animatedbottombar.AnimatedBottomBar
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentActorsListBinding
import uz.madgeeks.mimovie.presentation.BaseFragment
import uz.madgeeks.mimovie.presentation.adapter.ActorVerticalAdapter


@AndroidEntryPoint
class ActorsListFragment :
    BaseFragment<FragmentActorsListBinding>(FragmentActorsListBinding::inflate) {


    val viewModel: ActorsListViewModel by viewModels()
    val adapter by lazy {
        ActorVerticalAdapter()
    }

    override fun onViewCreate() {
        binding.allActorsList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.allActorsList.adapter = adapter

        adapter.setItemClickListener {
            val bundle = bundleOf("ACTOR_ID" to it)
            navController.navigate(R.id.action_navigation_actors_to_actorsFragment, bundle)
        }

        getAllActors()

        binding.search.isActivated = true

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.getSearchedActors(query = query)
                    viewModel.searchedActorsListLiveData.observe(viewLifecycleOwner) { data ->
                        if (!data.isNullOrEmpty()) {
                            adapter.setPersons(data)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getSearchedActors(query = it) }
                viewModel.searchedActorsListLiveData.observe(viewLifecycleOwner) {
                    if (!it.isNullOrEmpty()) {
                        adapter.setPersons(it)
                    }
                }
                return false
            }
        })
    }

    private fun getAllActors() {
        binding.allActorsTab.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab,
            ) {
                when (newTab.title) {
                    "DAILY" -> {
                        viewModel.actorsListLiveData.observe(viewLifecycleOwner) {
                            adapter.setPersons(it)
                        }
                    }
                    "WEEKLY" -> {
                        viewModel.dActorsListLiveData.observe(viewLifecycleOwner) {
                            adapter.setPersons(it)
                        }
                    }
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {

            }
        })

        viewModel.getAllWeeklyFamousPersons()
        viewModel.getAllDailyFamousPersons()
        viewModel.actorsListLiveData.observe(viewLifecycleOwner) {
            adapter.setPersons(it)
        }
    }
}