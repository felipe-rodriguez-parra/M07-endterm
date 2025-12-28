package com.example.spaceapps.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spaceapps.di.ServiceLocator
import com.example.spaceapps.domain.model.Rocket
import com.example.spaceapps.domain.usecase.GetRocketDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

sealed interface RocketDetailUiState {
    object Loading : RocketDetailUiState
    data class Success(val rocket: Rocket) : RocketDetailUiState
    data class Error(val message: String) : RocketDetailUiState
}

class RocketDetailViewModel(
    private val rocketId: String,
    private val getRocketDetailUseCase: GetRocketDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RocketDetailUiState>(RocketDetailUiState.Loading)
    val state: StateFlow<RocketDetailUiState> = _state.asStateFlow()

    init {
        getRocketDetailUseCase(rocketId)
            .onEach { rocket ->
                _state.value = if (rocket != null) {
                    RocketDetailUiState.Success(rocket)
                } else {
                    RocketDetailUiState.Error("Cohete no encontrado")
                }
            }
            .launchIn(viewModelScope)
    }
}

class RocketDetailViewModelFactory(
    private val application: Application,
    private val rocketId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketDetailViewModel::class.java)) {
            val useCase = ServiceLocator.provideGetRocketDetailUseCase(application)
            @Suppress("UNCHECKED_CAST")
            return RocketDetailViewModel(rocketId, useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

