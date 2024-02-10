package com.example.keepsilent.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.example.keepsilent.domain.repository.LocationRepository
import com.example.keepsilent.roomDatabase.LocationDatabase
import com.example.keepsilent.roomDatabase.LocationRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocationDatabase(app : Application) : LocationDatabase{
        return Room.databaseBuilder(
            app,
            LocationDatabase::class.java,
            "map_locations.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLocationRepository(db : LocationDatabase) : LocationRepository {
        return LocationRepositoryImp(db.dao)
    }
}

//Now we are good to go to add these dependency in MapViewModel