package com.example.keepsilent.domain.model

import androidx.room.PrimaryKey

data class Location (
    val lat : Double,
    val lng : Double,
    @PrimaryKey
    val id : Int? = null
)