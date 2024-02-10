package com.example.keepsilent.roomDatabase

import com.example.keepsilent.domain.model.Location
import com.example.keepsilent.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImp(
    private val dao : LocationDao
) : LocationRepository {

    override suspend fun upsertLocation(location: Location) {
        dao.upsertLocation(location.toLocationEntity())
    }

    override suspend fun deleteLocation(location: Location) {
        dao.deleteLocation(location.toLocationEntity())
    }

    override fun getLocations(): Flow<List<Location>> {
        return  dao.getLocations().map { locations ->
            locations.map { it.toLocation() }
        }
    }
}

//After creating repository implementation to have make application class to use this in viewModel