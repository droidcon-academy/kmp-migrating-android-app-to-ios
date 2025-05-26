package com.droidcon.cmp.di

import com.droidcon.cmp.core.data.Vault
import com.droidcon.cmp.core.presentation.Localization
import com.droidcon.cmp.jokes.data.database.JokesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {

        single<HttpClientEngine> {
            Darwin.create()
        }

        single<Vault> {
            Vault()
        }

        single<Localization> {
            Localization()
        }

        single { JokesDatabaseFactory() }

    }