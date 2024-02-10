package com.example.keepsilent.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocationDatabase : RoomDatabase() {
    abstract val dao : LocationDao
}

//3.After that for a clean code make a new package for domain.model and made a class called Location
//We are doing that because to show the location in ui we will access to database through that model class for clean code