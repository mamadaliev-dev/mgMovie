package uz.madgeeks.mimovie.data.actors.model.remote


import com.google.gson.annotations.SerializedName

data class ActorsResult(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("name")
    var name: String,
    @SerializedName("gender")
    var gender: Int,
    @SerializedName("known_for_department")
    var knownForDepartment: String,
    @SerializedName("profile_path")
    var profilePath: String,
    @SerializedName("id")
    var id: Long,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("media_type")
    var mediaType: String,
    @SerializedName("place_of_birth")
    var place_of_birth: String,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("biography")
    var biography : String
)