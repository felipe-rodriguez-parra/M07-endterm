package com.example.spaceapps.ui.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons // Asegúrate de tener esta también
import androidx.compose.material3.Icon // Y esta para el componente Iconimport androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.spaceapps.R
import com.example.spaceapps.ui.components.Rocket
import com.example.spaceapps.ui.theme.SpaceAppsTheme
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.Alignment

/**
 * Pantalla de Detalle de Cohete.
 * @param rocketId El ID del cohete a mostrar.
 * @param onBack Función de callback para volver a la lista.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceRocket(rocketId: String, onBack: () -> Unit) {
    // Simular la carga de datos del cohete (Usar ViewModel/Repository real)
    val rocket: Rocket? = remember { findRocketById(rocketId) }
    val context = LocalContext.current

    // Tarea #3: Función para abrir el enlace de Wikipedia
    fun openWikipediaLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(rocket?.name ?: stringResource(R.string.rocket_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        // Icono de flecha de retorno
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (rocket == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.error_rocket_not_found))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()) // Permitir scroll para textos largos
            ) {
                // Imagen Principal
                val imageUrl = rocket.flickrImages.firstOrNull() ?: "https://images.unsplash.com/photo-1516849841032-87cbac4d88f7?w=800"
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = stringResource(R.string.rocket_image_description, rocket.name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = rocket.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.label_description),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = rocket.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                    )

                    // Mínimo de 5 datos relevantes (Tarea #3)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(stringResource(R.string.data_first_flight), rocket.firstFlight)
                    DetailRow(stringResource(R.string.data_height), "${rocket.height?.meters ?: "?"} m")
                    DetailRow(stringResource(R.string.data_mass), "${(rocket.mass?.kg?.toFloat() ?: 0f) / 1000f} t")
                    DetailRow(stringResource(R.string.data_cost_per_launch), "$${(rocket.costPerLaunch.toFloat() / 1000000f).toInt()}M")
                    // Nota: La API de SpaceX v4 tiene 'success_rate_pct' y 'country', pero usamos datos del mock
                    DetailRow(stringResource(R.string.data_active_status), if (rocket.active) stringResource(R.string.status_active) else stringResource(R.string.status_retired))
                    Divider(modifier = Modifier.padding(vertical = 8.dp))


                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón de Wikipedia (Visible solo si hay URL)
                    // NOTA: Asumiendo que 'wikipedia' es un campo que debería existir en el modelo
                    val wikipediaUrl = "https://en.wikipedia.org/wiki/${rocket.name.replace(" ", "_")}" // Simulación de URL

                    if (wikipediaUrl.isNotBlank()) {
                        Button(
                            onClick = { openWikipediaLink(wikipediaUrl) },
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        ) {
                            Text(stringResource(R.string.button_more_info_wikipedia))
                        }
                    } else {
                        // El botón no se muestra si no hay URL (Tarea #3)
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Simulación de función de búsqueda (usar Room/Retrofit en la vida real)
private fun findRocketById(id: String): Rocket? {
    return mockRockets.find { it.id == id }
}

// Datos simulados para el Preview
private val mockRockets = listOf(
    Rocket(
        id = "falcon9",
        name = "Falcon 9",
        description = "Falcon 9 is a reusable, two-stage rocket designed and manufactured by SpaceX. It is the world’s first orbital class reusable rocket. Reusability allows SpaceX to re-fly the most expensive parts of the rocket, which in turn drives down the cost of access to space.",
        active = true,
        costPerLaunch = 67000000,
        firstFlight = "2010-06-04",
        flickrImages = listOf("https://farm1.staticflickr.com/929/28787338307_3453a17f9e_b.jpg"),
        height = com.example.spaceapps.ui.components.Dimension(70.0f),
        mass = com.example.spaceapps.ui.components.Mass(549054)
    )
)

@Preview(showBackground = true)
@Composable
fun PreviewSpaceRocket() {
    SpaceAppsTheme {
        SpaceRocket(rocketId = "falcon9", onBack = {})
    }
}