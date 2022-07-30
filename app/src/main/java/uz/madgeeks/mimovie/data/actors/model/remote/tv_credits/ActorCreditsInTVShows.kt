package uz.madgeeks.mimovie.data.actors.model.remote.tv_credits

data class ActorCreditsInTVShows(
    val cast: List<ActorCastInTVShows>,
    val crew: List<CrewInTVShows>,
    val id: Int
)