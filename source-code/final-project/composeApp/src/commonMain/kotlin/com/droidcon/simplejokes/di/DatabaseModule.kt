package com.droidcon.simplejokes.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.droidcon.simplejokes.jokes.data.database.JokesDao
import com.droidcon.simplejokes.jokes.data.database.JokesDatabase
import com.droidcon.simplejokes.jokes.data.database.JokesDatabaseFactory
import org.koin.dsl.module

val databaseModule = module {
    // DATABASE
    single<JokesDatabase> {
        get<JokesDatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<JokesDao> {
        get<JokesDatabase>().jokesDao
    }
}