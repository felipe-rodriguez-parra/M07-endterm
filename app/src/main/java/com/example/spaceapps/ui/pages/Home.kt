package com.example.spaceapps.ui.pages


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spaceapps.R
import com.example.spaceapps.ui.components.Rocket
import com.example.spaceapps.ui.components.RocketCard
import com.example.spaceapps.ui.components.mockRockets

// Ejemplo de estado de pantalla (Carga, Éxito, Error)
sealed class UiState {
    object Loading : UiState()
    data class Success(val rockets: List<Rocket>) : UiState()
    data class Error(val message: String) : UiState()
}

/**
 * Pantalla principal de la aplicación, muestra la lista de cohetes.
 * @param onRocketClick Función para navegar a la vista de detalle.
 * @param onLogout Función para cerrar sesión.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onRocketClick: (String) -> Unit, onLogout: () -> Unit) {

    // Usar estado en la vida real (ejemplo simple, usar ViewModel en producción)
    var currentState by remember { mutableStateOf<UiState>(UiState.Loading) }
    var searchText by remember { mutableStateOf("") }

    // Define el CoroutineScope ligado al ciclo de vida de Home
    val scope = rememberCoroutineScope()

    // Simular carga de datos (usar Retrofit/Room en el MVP real)
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500) // Simular carga de red
        currentState = UiState.Success(mockRockets)
    }

    // Filtrar cohetes
    val filteredRockets = when (currentState) {
        is UiState.Success -> {
            (currentState as UiState.Success).rockets.filter {
                it.name.contains(searchText, ignoreCase = true)
            }
        }
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) },
                actions = {
                    Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                        Text(stringResource(R.string.button_logout))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Campo de Búsqueda
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text(stringResource(R.string.search_rocket_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Contenido basado en el estado
            when (currentState) {
                UiState.Loading -> {
                    // Tarea #4 - Estado de carga
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
                is UiState.Error -> {
                    // Tarea #4 - Estado de error con reintento
                    val errorMessage = (currentState as UiState.Error).message
                    ErrorState(
                        message = errorMessage,
                        onRetry = {
                            // Lógica de reintento
                            currentState = UiState.Loading
                            // Simular reintento de carga
                            // Simular carga de datos (usar Retrofit/Room en el MVP real)
                            // También usa 'scope' para la carga inicial si lo prefieres, o mantén LaunchedEffect
                            LaunchedEffect(Unit) {
                                kotlinx.coroutines.delay(1500) // Simular carga de red
                                currentState = UiState.Success(mockRockets)
                            }
                        }
                    )
                }
                is UiState.Success -> {
                    if (filteredRockets.isEmpty()) {
                        // Tarea #4 - Estado Vacío (Sin resultados)
                        EmptyState(message = stringResource(R.string.empty_results_message))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(filteredRockets.size) { index ->
                                RocketCard(
                                    rocket = filteredRockets[index],
                                    onRocketClick = onRocketClick,
                                    // Simular el animationDelay de JSX (Tarea #2)
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            // Placeholder de icono
            imageVector = Icons.Filled.Menu,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ErrorState(message: String, onRetry: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetry as () -> Unit) {
            Text(stringResource(R.string.action_retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    // Definición de mockRockets (debería estar en el archivo del componente real)
    val mockRockets = listOf(
        Rocket("f1", "Falcon 1", "Rocket 1 description...", true, 6700000, "2006-03-24", listOf(""), com.example.spaceapps.ui.components.Dimension(18.3f), com.example.spaceapps.ui.components.Mass(30167)),
        Rocket("f9", "Falcon 9", "Rocket 9 description...", true, 67000000, "2010-06-04", listOf(""), com.example.spaceapps.ui.components.Dimension(70f), com.example.spaceapps.ui.components.Mass(549054))
    )

    // Asignar el mock para el preview
    com.example.spaceapps.ui.components.mockRockets
    Home(onRocketClick = {}, onLogout = {})
}

// Datos simulados para el Preview de la Home
private var mockRockets = listOf(
    Rocket("f1", "Falcon 1", "Rocket 1 description...", true, 6700000, "2006-03-24", listOf(""), com.example.spaceapps.ui.components.Dimension(18.3f), com.example.spaceapps.ui.components.Mass(30167)),
    Rocket("f9", "Falcon 9", "Rocket 9 description...", true, 67000000, "2010-06-04", listOf(""), com.example.spaceapps.ui.components.Dimension(70f), com.example.spaceapps.ui.components.Mass(549054))
)