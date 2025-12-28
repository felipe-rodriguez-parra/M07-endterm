package com.example.spaceapps.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.spaceapps.R
import com.example.spaceapps.domain.model.Rocket
import com.example.spaceapps.ui.theme.SpaceAppsTheme
import com.example.spaceapps.ui.theme.SpaceTheme


/**
 * Componente que representa la tarjeta de un cohete.
 *
 * @param rocket El objeto Rocket a mostrar.
 * @param onRocketClick Función de callback para manejar el clic en la tarjeta.
 * @param modifier Modificador de Compose para aplicar estilos.
 */
@Composable
fun RocketCard(
    rocket: Rocket,
    onRocketClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val spaceColors = SpaceTheme.colors
    val colorScheme = MaterialTheme.colorScheme

    var isHovered by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.07f else 1.0f,
        animationSpec = tween(700)
    )
    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 16f else 0f,
        animationSpec = tween(500)
    )
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = spaceColors.surfaceCard.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = colorScheme.primary.copy(alpha = 0.2f),
                spotColor = colorScheme.primary.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable { onRocketClick(rocket.id) }
            .padding(1.dp) // Simula el borde
            .background(
                color = if (isHovered) colorScheme.primary.copy(alpha = 0.3f) else spaceColors.surfaceOverlay,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isHovered = it.isFocused }
        ) {
            // Contenedor de la Imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp) // h-56
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                val imageUrl = rocket.flickrImages.firstOrNull() ?: "https://images.unsplash.com/photo-1516849841032-87cbac4d88f7?w=800"
                val painter = rememberAsyncImagePainter(model = imageUrl)

                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.rocket_image_description, rocket.name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                )

                // Gradiente de superposición
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    spaceColors.surfaceCard.copy(alpha = 0.9f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
            }

            // Contenido de Texto y Estadísticas
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = rocket.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isHovered) colorScheme.primary else spaceColors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = rocket.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = spaceColors.textSecondary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Estadísticas (Grid de 3 columnas)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Altura
                    StatItem(
                        icon = Icons.Default.Height,
                        label = stringResource(R.string.stat_height),
                        value = "${rocket.heightMeters}m",
                        modifier = Modifier.weight(1f)
                    )
                    // Masa
                    StatItem(
                        icon = Icons.Default.Scale,
                        label = stringResource(R.string.stat_mass),
                        value = "${rocket.massKg?.let { it.toFloat() / 1000f } ?: "?"}t",
                        modifier = Modifier.weight(1f)
                    )
                    // Primer Vuelo
                    StatItem(
                        icon = Icons.Default.CalendarToday,
                        label = stringResource(R.string.stat_first_flight),
                        value = rocket.firstFlight.split("-").firstOrNull() ?: "?",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // CTA e Información de Costo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val costInMillions = (rocket.costPerLaunch.toFloat() / 1000000f).toInt()
                    Text(
                        text = stringResource(R.string.cost_per_launch, costInMillions),
                        style = MaterialTheme.typography.bodyMedium,
                        color = spaceColors.textSecondary
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.view_details),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.primary
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = null,
                            tint = colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Stat element used in RocketCard to display an icon, label, and value.
 */
@Composable
fun StatItem(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    val spaceColors = SpaceTheme.colors
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .background(spaceColors.surfaceOverlay, RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = spaceColors.textSecondary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = spaceColors.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRocketCard() {
    SpaceAppsTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            RocketCard(
                rocket = Rocket(
                    id = "falcon9",
                    name = "Falcon 9",
                    description = "Falcon 9 is a reusable, two-stage rocket designed and manufactured by SpaceX.",
                    active = true,
                    costPerLaunch = 67000000,
                    firstFlight = "2010-06-04",
                    flickrImages = listOf("https://farm1.staticflickr.com/929/28787338307_3453a17f9e_b.jpg"),
                    heightMeters = 70.0,
                    massKg = 549054
                ),
                onRocketClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewRocketCardLight() {
    SpaceAppsTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            RocketCard(
                rocket = Rocket(
                    id = "falcon9",
                    name = "Falcon 9",
                    description = "Falcon 9 is a reusable, two-stage rocket designed and manufactured by SpaceX.",
                    active = true,
                    costPerLaunch = 67000000,
                    firstFlight = "2010-06-04",
                    flickrImages = listOf("https://farm1.staticflickr.com/929/28787338307_3453a17f9e_b.jpg"),
                    heightMeters = 70.0,
                    massKg = 549054
                ),
                onRocketClick = {}
            )
        }
    }
}
