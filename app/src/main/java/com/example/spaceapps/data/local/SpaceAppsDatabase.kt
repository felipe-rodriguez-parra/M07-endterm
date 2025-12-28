package com.example.spaceapps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RocketEntity::class], version = 1, exportSchema = false)
abstract class SpaceAppsDatabase : RoomDatabase() {
    abstract fun rocketDao(): RocketDao

    companion object {
        const val NAME = "spaceapps.db"
    }
}
