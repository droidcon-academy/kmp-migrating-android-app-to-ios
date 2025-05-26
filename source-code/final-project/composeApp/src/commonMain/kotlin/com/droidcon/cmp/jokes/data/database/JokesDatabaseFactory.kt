package com.droidcon.cmp.jokes.data.database

import androidx.room.RoomDatabase

expect class JokesDatabaseFactory {
    fun create(): RoomDatabase.Builder<JokesDatabase>
}