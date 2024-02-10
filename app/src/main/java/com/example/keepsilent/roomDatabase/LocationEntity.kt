package com.example.keepsilent.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity (
    val lat : Double,
    val lng : Double,
    @PrimaryKey
    val id : Int? = null
)

//1.After that create the dao to define the actions you wanna perform