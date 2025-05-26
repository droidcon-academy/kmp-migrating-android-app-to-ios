package com.droidcon.cmp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.droidcon.cmp.core.data.PreferencesDataSourceImpl
import com.droidcon.cmp.core.domain.datasource.PreferencesDataSource
import com.droidcon.cmp.jokes.data.JokesRepositoryImpl
import com.droidcon.cmp.jokes.data.database.JokesDao
import com.droidcon.cmp.jokes.data.database.JokesDatabase
import com.droidcon.cmp.jokes.data.database.JokesDatabaseFactory
import com.droidcon.cmp.jokes.data.network.JokesApiService
import com.droidcon.cmp.jokes.domain.JokesRepository
import com.droidcon.cmp.jokes.presentation.screens.joke_details.JokesDetailsViewModel
import com.droidcon.cmp.jokes.presentation.screens.jokes_list.JokesListViewModel
import com.droidcon.cmp.jokes.presentation.screens.preferences.PreferencesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    // API
    single<HttpClient> {
        HttpClient(engine = get()) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // Be resilient to new fields in the JSON
                    prettyPrint = true       // Useful for logging
                    isLenient = true         // Be lenient to no-compliant JSON features

                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.d(message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url {
                    takeFrom("https://official-joke-api.appspot.com")
                }
            }
        }
    }
    single { JokesApiService(get()) }

    // DATABASE
    single<JokesDatabase> {
        get<JokesDatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<JokesDao> {
        get<JokesDatabase>().jokesDao
    }

    // Preferences
    singleOf(::PreferencesDataSourceImpl).bind<PreferencesDataSource>()

    // Jokes Repository
    singleOf(::JokesRepositoryImpl).bind<JokesRepository>()

    // ViewModels
    viewModelOf(::JokesListViewModel)
    viewModelOf(::JokesDetailsViewModel)
    viewModelOf(::PreferencesViewModel)
}