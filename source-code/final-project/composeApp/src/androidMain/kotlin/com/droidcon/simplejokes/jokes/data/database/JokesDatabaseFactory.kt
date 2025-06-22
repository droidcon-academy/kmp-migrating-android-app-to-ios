package com.droidcon.simplejokes.jokes.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class JokesDatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<JokesDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(JokesDatabase.DB_NAME)

        println("DB file path cmp: ${dbFile.absolutePath}")

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}