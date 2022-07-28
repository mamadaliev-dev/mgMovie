package uz.madgeeks.mimovie.data.actors_list.model

data class LatestsActors(
    val adult: Boolean,
    val also_known_as: List<String>,
    val biography: String? = null,
    val birthday: String? = null,
    val deathday: String? = null,
    val gender: Int,
    val homepage: String? = null,
    val id: Int,
    val imdb_id: String? = null,
    val name: String,
    val place_of_birth: String? = null,
    val popularity: Int,
    val profile_path: String? = null,
)