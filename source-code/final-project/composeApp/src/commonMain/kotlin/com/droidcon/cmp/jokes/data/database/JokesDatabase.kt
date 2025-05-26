package com.droidcon.cmp.jokes.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.droidcon.cmp.jokes.data.model.JokeEntity

@Database(entities = [JokeEntity::class], version = 1, exportSchema = false)
@ConstructedBy(JokesDatabaseConstructor::class)
abstract class JokesDatabase : RoomDatabase() {
    abstract val jokesDao: JokesDao

    companion object {
        const val DB_NAME = "jokes.db"
    }
}