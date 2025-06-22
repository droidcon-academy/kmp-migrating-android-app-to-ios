package com.droidcon.simplejokes.jokes.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class JokesDatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<JokesDatabase> {
        return Room.databaseBuilder(
            context = context.applicationContext,
            name = JokesDatabase.DB_NAME
        )
    }
}