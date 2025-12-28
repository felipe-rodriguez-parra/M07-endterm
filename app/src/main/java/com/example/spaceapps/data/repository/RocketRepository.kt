package com.example.spaceapps.data.repository

import com.example.spaceapps.data.api.SpaceXApiService
import com.example.spaceapps.data.local.RocketDao
import com.example.spaceapps.data.local.RocketEntity
import com.example.spaceapps.domain.model.Rocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RocketRepository(
    private val api: SpaceXApiService,
    private val dao: RocketDao
) {
    fun observeRockets(): Flow<List<Rocket>> = dao.observeRockets().map { list ->
        list.map { it.toDomain() }
    }

    fun observeRocket(id: String): Flow<Rocket?> = dao.observeRocket(id).map { it?.toDomain() }

    suspend fun refresh() {
        val remote = api.getRockets()
        val entities = remote.map { dto ->
            RocketEntity(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                active = dto.active,
                costPerLaunch = dto.costPerLaunch ?: 0L,
                firstFlight = dto.firstFlight,
                imageUrl = dto.flickrImages.firstOrNull(),
                heightMeters = dto.height?.meters,
                massKg = dto.mass?.kg
            )
        }
        dao.upsertAll(entities)
    }
}

private fun RocketEntity.toDomain(): Rocket = Rocket(
    id = id,
    name = name,
    description = description,
    active = active,
    costPerLaunch = costPerLaunch,
    firstFlight = firstFlight,
    flickrImages = listOfNotNull(imageUrl),
    heightMeters = heightMeters,
    massKg = massKg
)
