package uz.madgeeks.mimovie.data.base

sealed class BaseNetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean? = null
) {
    class Success<T>(data: T) : BaseNetworkResult<T>(data = data)
    class Error<T>(message: String?) : BaseNetworkResult<T>(message = message)
    class Loading<T>(isLoading: Boolean?) : BaseNetworkResult<T>(isLoading = isLoading)
}