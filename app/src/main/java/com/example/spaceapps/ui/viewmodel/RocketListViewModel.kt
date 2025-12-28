package com.example.spaceapps.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spaceapps.di.ServiceLocator
import com.example.spaceapps.domain.model.Rocket
import com.example.spaceapps.domain.usecase.GetRocketsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RocketListUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val query: String = "",
    val rockets: List<Rocket> = emptyList()
)

class RocketListViewModel(
    private val getRockets: GetRocketsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RocketListUiState())
    val state: StateFlow<RocketListUiState> = _state.asStateFlow()

    init {
        refresh()
        viewModelScope.launch {
            getRockets().collect { rockets ->
                _state.update { it.copy(rockets = rockets, isLoading = false, error = null) }
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching { getRockets.refresh() }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
                .onSuccess { _state.update { it.copy(isLoading = false) } }
        }
    }
}

class RocketListViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketListViewModel::class.java)) {
            val useCase = ServiceLocator.provideGetRocketsUseCase(application)
            @Suppress("UNCHECKED_CAST")
            return RocketListViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
