package com.droidcon.simplejokes.di

import com.droidcon.simplejokes.jokes.data.database.JokesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {

        single<HttpClientEngine> {
            Darwin.create()
        }



        single { JokesDatabaseFactory() }

    }