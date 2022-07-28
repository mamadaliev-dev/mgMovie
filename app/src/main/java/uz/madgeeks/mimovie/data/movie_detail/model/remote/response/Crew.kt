package uz.madgeeks.mimovie.data.movie_detail.model.remote.response

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("gender")
    var gender: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("known_for_department")
    var knownForDepartment: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("original_name")
    var original_name: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("profile_path")
    var profilePath: String,
    @SerializedName("credit_id")
    var credit_id: String,
    @SerializedName("department")
    var department: String,
    @SerializedName("job")
    var job: String,
)