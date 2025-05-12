package com.droidcon.myandroidapp.di

import android.content.Context
import androidx.room.Room
import com.droidcon.myandroidapp.jokes.data.database.JokesDao
import com.droidcon.myandroidapp.jokes.data.database.JokesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJokesDatabase(@ApplicationContext context: Context): JokesDatabase {
        return Room.databaseBuilder(
            context,
            JokesDatabase::class.java,
            "jokes_database"
        )
            .build()
    }

    @Provides
    fun provideJokesDao(database: JokesDatabase): JokesDao {
        return database.jokesDao()
    }
}