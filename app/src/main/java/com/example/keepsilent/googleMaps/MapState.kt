package com.example.keepsilent.googleMaps

import com.example.keepsilent.domain.model.Location
import com.google.maps.android.compose.MapProperties

data class MapState (
    val properties : MapProperties = MapProperties(),
    val locations : List<Location> = emptyList(),
    val isFallOut : Boolean = false
)