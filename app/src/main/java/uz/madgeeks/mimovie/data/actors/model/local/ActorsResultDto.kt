package uz.madgeeks.mimovie.data.actors.model.local

import com.google.gson.annotations.SerializedName

data class ActorsResultDto(
    var adult: Boolean ?= null,
    var name: String ?= null,
    var gender: Int ?= null,
    var knownForDepartment: String ?= null,
    @SerializedName("profile_path")
    var profilePath: String ?= null,
    var id: Long,
    var popularity: Double ?= null,
    var mediaType: String ?= null,
    var place_of_birth: String ?= null,
    var birthday: String ?= null,
    var biography : String ?= null,
)