package uz.madgeeks.mimovie.data.season_details.models

import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.Crew

data class Episode(
    val air_date: String,
    val crew: List<Crew>,
    val episode_number: Int,
    val guest_stars: List<GuestStar>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int
)