package com.droidcon.simplejokes.di

import com.droidcon.simplejokes.jokes.data.database.JokesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> {
        OkHttp.create()
    }

    single { JokesDatabaseFactory(androidApplication()) }
}