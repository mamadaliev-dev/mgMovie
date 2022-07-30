package uz.madgeeks.mimovie.presentation.actors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.madgeeks.mimovie.data.actors.model.local.ActorsResultDto
import uz.madgeeks.mimovie.data.actors.model.remote.movie_credits.ActorCastInMovies
import uz.madgeeks.mimovie.data.actors.model.remote.tv_credits.ActorCastInTVShows
import uz.madgeeks.mimovie.data.base.BaseNetworkResult
import uz.madgeeks.mimovie.domain.actors.ActorsUseCase
import javax.inject.Inject

@HiltViewModel
class ActorsViewModel @Inject constructor(
    private val actorsUseCase: ActorsUseCase,
) : ViewModel() {

    private val actorModel = MutableLiveData<ActorsResultDto>()
    val actorModelLiveData: LiveData<ActorsResultDto> get() = actorModel

    private val credits = MutableLiveData<List<ActorCastInMovies>>()
    val creditsLiveData: LiveData<List<ActorCastInMovies>> get() = credits

    private val tvCredits = MutableLiveData<List<ActorCastInTVShows>>()
    val tvCreditsLiveData: LiveData<List<ActorCastInTVShows>> get() = tvCredits

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    fun getAllFamousPersons(id: Long) {
        viewModelScope.launch {
            actorsUseCase.getPersonDetailById(id).catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            actorModel.value = list
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

    fun getMovieCreditsById(id: Long) {
        viewModelScope.launch {
            actorsUseCase.getMovieCreditsById(id).catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            credits.value = list.cast
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

    fun getTVCreditsById(id: Long) {
        viewModelScope.launch {
            actorsUseCase.getTVCreditsById(id).catch {
                //xatolik beriladi
                Log.d("DDDD", "getServicesResponse: $this")
            }.collect { result ->
                when (result) {
                    is BaseNetworkResult.Success -> {
                        result.data?.let { list ->
                            tvCredits.value = list.cast
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
}