package com.droidcon.cmp.di

import com.droidcon.cmp.core.data.Vault
import com.droidcon.cmp.core.presentation.Localization
import com.droidcon.cmp.jokes.data.database.JokesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {

        single<HttpClientEngine> {
            OkHttp.create()
        }

        single<Vault> {
            Vault(androidApplication())
        }

        single<Localization> {
            Localization(androidApplication())
        }

        single { JokesDatabaseFactory(androidApplication()) }

    }