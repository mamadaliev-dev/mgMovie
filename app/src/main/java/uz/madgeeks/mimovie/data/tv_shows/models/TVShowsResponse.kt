package uz.madgeeks.mimovie.data.tv_shows.models

data class TVShowsResponse(
    val page: Int,
    val results: List<TVShowsResult>,
    val total_pages: Int,
    val total_results: Int
)