package com.example.spaceapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val active: Boolean,
    val costPerLaunch: Long,
    val firstFlight: String,
    val imageUrl: String?,
    val heightMeters: Double?,
    val massKg: Long?
)
