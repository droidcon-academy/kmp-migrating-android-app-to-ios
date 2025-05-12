package com.droidcon.myandroidapp.di

import com.droidcon.myandroidapp.core.data.PreferencesDataSourceImpl
import com.droidcon.myandroidapp.core.domain.datasource.PreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindPreferencesDataSource(
        impl: PreferencesDataSourceImpl
    ): PreferencesDataSource
}