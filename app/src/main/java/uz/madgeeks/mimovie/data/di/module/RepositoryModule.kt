package uz.madgeeks.mimovie.data.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.madgeeks.mimovie.data.actors.ActorsRepositoryImpl
import uz.madgeeks.mimovie.data.actors.model.ActorsService
import uz.madgeeks.mimovie.data.actors_list.ActorsListRepositoryImpl
import uz.madgeeks.mimovie.data.genre.GenreRepositoryImpl
import uz.madgeeks.mimovie.data.genre.GenreService
import uz.madgeeks.mimovie.data.home.HomeRepositoryImpl
import uz.madgeeks.mimovie.data.home.HomeService
import uz.madgeeks.mimovie.domain.home.HomeRepository
import uz.madgeeks.mimovie.domain.movie_detail.MovieDetailRepository
import uz.madgeeks.mimovie.data.movie_detail.MovieDetailRepositoryImpl
import uz.madgeeks.mimovie.data.movie_detail.model.MovieDetailService
import uz.madgeeks.mimovie.domain.actors.ActorsListRepository
import uz.madgeeks.mimovie.domain.actors.ActorsRepository
import uz.madgeeks.mimovie.domain.genre.GenreRepository
import uz.madgeeks.mimovie.data.actors_list.model.ActorsListService
import uz.madgeeks.mimovie.data.search.SearchRepositoryImpl
import uz.madgeeks.mimovie.data.search.SearchService
import uz.madgeeks.mimovie.data.season_details.SeasonDetailRepoImpl
import uz.madgeeks.mimovie.data.season_details.SeasonDetailService
import uz.madgeeks.mimovie.data.tv_shows.TVShowsRepositoryImpl
import uz.madgeeks.mimovie.data.tv_shows.TVShowsService
import uz.madgeeks.mimovie.data.tv_show_details.TVShowDetailRepositoryImpl
import uz.madgeeks.mimovie.data.tv_show_details.TVShowDetailService
import uz.madgeeks.mimovie.domain.search.SearchRepository
import uz.madgeeks.mimovie.domain.season_details.SeasonDetailRepository
import uz.madgeeks.mimovie.domain.tv_shows.TVShowsRepository
import uz.madgeeks.mimovie.domain.tv_show_details.TVShowDetailRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideHomeRepository(mainService: HomeService): HomeRepository {
        return HomeRepositoryImpl(mainService)
    }

    @Provides
    fun provideMovieDetailRepository(mainService: MovieDetailService): MovieDetailRepository {
        return MovieDetailRepositoryImpl(mainService)
    }

    @Provides
    fun provideGenreRepository(mainService: GenreService): GenreRepository {
        return GenreRepositoryImpl(mainService)
    }

    @Provides
    fun provideActorRepository(mainService: ActorsService): ActorsRepository {
        return ActorsRepositoryImpl(mainService)
    }

    @Provides
    fun provideActorListRepository(mainService: ActorsListService): ActorsListRepository {
        return ActorsListRepositoryImpl(mainService)
    }

    @Provides
    fun provideSearchRepository(mainService: SearchService): SearchRepository {
        return SearchRepositoryImpl(mainService)
    }

    @Provides
    fun provideTVShowsRepository(mainService: TVShowsService): TVShowsRepository {
        return TVShowsRepositoryImpl(mainService)
    }

    @Provides
    fun provideTVShowsDetailsRepository(mainService: TVShowDetailService): TVShowDetailRepository {
        return TVShowDetailRepositoryImpl(mainService)
    }

    @Provides
    fun provideSeasonDetailRepository(mainService: SeasonDetailService): SeasonDetailRepository {
        return SeasonDetailRepoImpl(mainService)
    }
}