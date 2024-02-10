package com.example.keepsilent.googleMaps

import com.example.keepsilent.domain.model.Location
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object ToggleFalloutMap : MapEvent()
    data class OnMapLongClick(val latlng : LatLng) : MapEvent()
    data class OnInfoWindowLongClick(val location: Location) : MapEvent()
}