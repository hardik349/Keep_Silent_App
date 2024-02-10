package com.example.keepsilent.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLocation (location : LocationEntity)

    @Delete
    suspend fun deleteLocation (location: LocationEntity)

    @Query("SELECT * FROM locationentity")
    fun getLocations() : Flow<List<LocationEntity>>

}

//2.After that create the database to store locally