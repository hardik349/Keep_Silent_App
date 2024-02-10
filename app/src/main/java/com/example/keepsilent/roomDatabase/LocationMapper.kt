package com.example.keepsilent.roomDatabase

import com.example.keepsilent.domain.model.Location

fun LocationEntity.toLocation() : Location {
    return Location(
        lat = lat,
        lng = lat,
        id = id
    )
}

fun Location.toLocationEntity() : LocationEntity {
    return LocationEntity(
        lat = lat,
        lng = lat,
        id = id
    )
}