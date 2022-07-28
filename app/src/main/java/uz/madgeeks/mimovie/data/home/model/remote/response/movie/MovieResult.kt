package uz.madgeeks.mimovie.data.home.model.remote.response.movie


import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Long,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String ?=null,
    @SerializedName("release_date")
    var releaseDate: String ?=null,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
)