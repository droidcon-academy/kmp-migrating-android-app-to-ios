package com.droidcon.myandroidapp.di

import com.droidcon.myandroidapp.jokes.data.JokesRepositoryImpl
import com.droidcon.myandroidapp.jokes.domain.JokesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class JokesRepositoryModule {

    @Binds
    abstract fun bindJokesRepository(
        jokesRepositoryImpl: JokesRepositoryImpl
    ): JokesRepository
}