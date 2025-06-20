package com.droidcon.simplejokes.jokes.data.database

import androidx.room.RoomDatabase

expect class JokesDatabaseFactory {
    fun create(): RoomDatabase.Builder<JokesDatabase>
}