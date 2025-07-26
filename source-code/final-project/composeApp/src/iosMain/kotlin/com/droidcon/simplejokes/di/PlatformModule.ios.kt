package com.droidcon.simplejokes.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.droidcon.simplejokes.jokes.data.database.JokesDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val platformModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }


}
