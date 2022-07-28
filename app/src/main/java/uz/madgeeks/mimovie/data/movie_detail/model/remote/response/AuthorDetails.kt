package uz.madgeeks.mimovie.data.movie_detail.model.remote.response

data class AuthorDetails(
    val name: String,
    val username: String,
    val avatar_path: String? = null,
    val rating: Int? = null,
)