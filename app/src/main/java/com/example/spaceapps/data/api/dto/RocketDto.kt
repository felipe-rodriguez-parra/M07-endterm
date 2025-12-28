package com.example.spaceapps.data.api.dto

import com.squareup.moshi.Json

data class RocketDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "active") val active: Boolean,
    @Json(name = "cost_per_launch") val costPerLaunch: Long?,
    @Json(name = "first_flight") val firstFlight: String,
    @Json(name = "flickr_images") val flickrImages: List<String>,
    @Json(name = "height") val height: DimensionDto?,
    @Json(name = "mass") val mass: MassDto?
)

data class DimensionDto(
    @Json(name = "meters") val meters: Double?,
    @Json(name = "feet") val feet: Double?
)

data class MassDto(
    @Json(name = "kg") val kg: Long?,
    @Json(name = "lb") val lb: Long?
)
