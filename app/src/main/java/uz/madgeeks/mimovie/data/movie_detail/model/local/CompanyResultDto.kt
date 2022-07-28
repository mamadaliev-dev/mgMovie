package uz.madgeeks.mimovie.data.movie_detail.model.local

data class CompanyResultDto(
    var description: String ?= null,
    var headquarters: String ?= null,
    var homepage: String ?= null,
    var id: Int,
    var logo_path: String ?= null,
    var name: String ?= null,
    var origin_country: String ?= null,
)