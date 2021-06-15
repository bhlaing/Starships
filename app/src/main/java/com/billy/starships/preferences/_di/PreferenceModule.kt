package com.billy.starships.preferences._di

import com.billy.starships.preferences.PreferenceManager
import com.billy.starships.preferences.PreferenceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PreferenceModule {
    @Singleton
    @Provides
    fun providePreferenceManager(preferenceManagerImpl: PreferenceManagerImpl): PreferenceManager =
        preferenceManagerImpl
}