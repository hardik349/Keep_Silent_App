package com.example.keepsilent.googleMaps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepsilent.domain.model.Location
import com.example.keepsilent.domain.repository.LocationRepository
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())

    //We have added the events in viewModel handle change from database
    init {
        viewModelScope.launch{
            repository.getLocations().collectLatest { locations ->
                state = state.copy(
                    locations = locations
                )
            }
        }
    }

    fun Event(event : MapEvent){
        when(event){
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if(state.isFallOut){
                            null
                        }else{
                            MapStyleOptions(MapStyle.json)
                        }
                    ),
                        isFallOut = !state.isFallOut
                )
            }
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.upsertLocation(Location(
                        event.latlng.latitude,
                        event.latlng.longitude
                    ))
                }
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteLocation(event.location)
                }
            }
        }
    }
}