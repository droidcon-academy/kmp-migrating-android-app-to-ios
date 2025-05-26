package com.droidcon.cmp.jokes.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object JokesDatabaseConstructor : RoomDatabaseConstructor<JokesDatabase> {
    override fun initialize(): JokesDatabase
}