package uz.madgeeks.mimovie.data.movie_detail.model.remote.response


import com.google.gson.annotations.SerializedName
import uz.madgeeks.mimovie.data.genre.local.Genre
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.ProductionCountry
import uz.madgeeks.mimovie.data.movie_detail.model.remote.response.SpokenLanguage

data class MovieDetailResponse(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection,
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("genres")
    var genres: List<Genre>,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("id")
    var id: Long,
    @SerializedName("imdb_id")
    var imdbId: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("production_companies")
    var productionCompanies: List<CompanyResult>,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("revenue")
    var revenue: Int,
    @SerializedName("runtime")
    var runtime: Int,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status")
    var status: String,
    @SerializedName("tagline")
    var tagline: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int,
)