package com.example.spaceapps.data.api

import com.example.spaceapps.data.api.dto.RocketDto
import retrofit2.http.GET

interface SpaceXApiService {
    @GET("rockets")
    suspend fun getRockets(): List<RocketDto>
}

