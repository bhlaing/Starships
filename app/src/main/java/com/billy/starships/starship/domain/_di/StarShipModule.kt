package com.billy.starships.starship.domain._di

import com.billy.starships.starship.data.StarshipService
import com.billy.starships.starship.data.StarshipServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StarShipModule {

    @Singleton
    @Provides
    fun provideStarShipService(starshipServiceImpl: StarshipServiceImpl): StarshipService =
        starshipServiceImpl
}