package com.example.spaceapps.domain.model

data class Rocket(
    val id: String,
    val name: String,
    val description: String,
    val active: Boolean,
    val costPerLaunch: Long,
    val firstFlight: String,
    val flickrImages: List<String>,
    val heightMeters: Double?,
    val massKg: Long?
)

