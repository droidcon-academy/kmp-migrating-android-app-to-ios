package com.droidcon.simplejokes.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.droidcon.simplejokes.jokes.data.database.JokesDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> {
        OkHttp.create()
    }
}