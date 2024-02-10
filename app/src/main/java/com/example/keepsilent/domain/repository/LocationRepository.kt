package com.example.keepsilent.domain.repository

import com.example.keepsilent.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun upsertLocation(location: Location)

    suspend fun deleteLocation(location: Location)

    fun getLocations() : Flow<List<Location>>
}