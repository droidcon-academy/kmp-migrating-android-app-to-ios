package com.droidcon.simplejokes.di

import com.droidcon.simplejokes.jokes.data.database.JokesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }

    single { JokesDatabaseFactory() }
}