package com.example.spaceapps.domain.usecase

import com.example.spaceapps.data.repository.RocketRepository
import com.example.spaceapps.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

class GetRocketDetailUseCase(private val repository: RocketRepository) {
    operator fun invoke(id: String): Flow<Rocket?> = repository.observeRocket(id)
}

