package com.droidcon.myandroidapp.jokes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droidcon.myandroidapp.jokes.data.model.JokeEntity

@Database(entities = [JokeEntity::class], version = 2, exportSchema = false)
abstract class JokesDatabase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}