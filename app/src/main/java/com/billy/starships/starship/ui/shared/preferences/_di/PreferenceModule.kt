package com.billy.starships.starship.ui.shared.preferences._di

import com.billy.starships.starship.ui.shared.preferences.PreferenceManager
import com.billy.starships.starship.ui.shared.preferences.PreferenceManagerImpl
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