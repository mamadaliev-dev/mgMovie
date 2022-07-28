package uz.madgeeks.mimovie.presentation.actors_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.mimovie.data.actors.model.local.ActorsResultDto
import uz.madgeeks.mimovie.data.actors_list.model.LatestsActors
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.domain.actors.ActorsListUseCase
import javax.inject.Inject

@HiltViewModel
class ActorsListViewModel @Inject constructor(
    private val mainUseCase: ActorsListUseCase,
) : ViewModel() {
    private val actorsList = MutableLiveData<List<ActorsResultDto>>()
    val actorsListLiveData: LiveData<List<ActorsResultDto>> get() = actorsList

    private val searchedActorsList = MutableLiveData<List<ActorsResultDto>>()
    val searchedActorsListLiveData: LiveData<List<ActorsResultDto>> get() = searchedActorsList

    private val dActorsList = MutableLiveData<List<ActorsResultDto>>()
    val dActorsListLiveData: LiveData<List<ActorsResultDto>> get() = dActorsList

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getAllWeeklyFamousPersons() {
        viewModelScope.launch {
            mainUseCase.getAllWeeklyFamousPersons().catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            actorsList.value = list.results?.map {
                                ActorsResultDto(
                                    adult = it.adult,
                                    name = it.name,
                                    gender = it.gender,
                                    knownForDepartment = it.knownForDepartment,
                                    profilePath = it.profilePath,
                                    id = it.id,
                                    popularity = it.popularity,
                                    mediaType = it.mediaType,
                                    place_of_birth = it.place_of_birth,
                                    birthday = it.birthday,
                                )
                            }
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        Log.d("DDDD", "XATOLIK")
                    }
                    is BaseNetworkResult.Loading -> {
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

    fun getAllDailyFamousPersons() {
        viewModelScope.launch {
            mainUseCase.getAllDailyFamousPersons().catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            dActorsList.value = list.results?.map {
                                ActorsResultDto(
                                    adult = it.adult,
                                    name = it.name,
                                    gender = it.gender,
                                    knownForDepartment = it.knownForDepartment,
                                    profilePath = it.profilePath,
                                    id = it.id,
                                    popularity = it.popularity,
                                    mediaType = it.mediaType,
                                    place_of_birth = it.place_of_birth,
                                    birthday = it.birthday,
                                )
                            }
                            list.results?.forEach { item ->
                                Log.d("DDDD", "${item.name} siii")
                            }

                        }
                    }
                    is BaseNetworkResult.Error -> {
                        Log.d("DDDD", "XATOLIK")
                    }
                    is BaseNetworkResult.Loading -> {
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }

    fun getSearchedActors(query: String) {
        viewModelScope.launch {
            mainUseCase.getSearchedActors(query).catch {
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            searchedActorsList.value = list.results?.map {
                                ActorsResultDto(
                                    adult = it.adult,
                                    name = it.name,
                                    gender = it.gender,
                                    knownForDepartment = it.knownForDepartment,
                                    profilePath = it.profilePath,
                                    id = it.id,
                                    popularity = it.popularity,
                                    mediaType = it.mediaType,
                                    place_of_birth = it.place_of_birth,
                                    birthday = it.birthday,
                                )
                            }
                        }
                    }
                    is BaseNetworkResult.Error -> {
                        result.message.let {
                            _errorLiveData.value = it
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        result.isLoading?.let {
                            _isLoadingLiveData.value = it
                        }
                    }
                }
            }
        }
    }
}