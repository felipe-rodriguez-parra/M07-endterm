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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import com.example.spaceapps.R // Necesitarás este archivo en tu proyecto

// Definición del modelo de datos para reflejar la API de SpaceX (simplificado)
data class Rocket(
    val id: String,
    val name: String,
    val description: String,
    val active: Boolean,
    val costPerLaunch: Long,
    val firstFlight: String, // "YYYY-MM-DD"
    val flickrImages: List<String>,
    val height: Dimension?,
    val mass: Mass?
)

data class Dimension(val meters: Float)
data class Mass(val kg: Long)


// Definición de colores principales (simulando los del JSX/Tailwind)
val PrimaryPurple = Color(0xFF6750A4) // Similar a #6750a4
val BackgroundDark = Color(0xFF1E1E32) // Similar a #1e1e32
val TextGray = Color(0xFF9E9E9E)

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
    // Estado para manejar la animación de hover/elevación
    var isHovered by remember { mutableStateOf(false) }

    // Animación para la escala de la imagen y la elevación de la tarjeta
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.07f else 1.0f,
        animationSpec = tween(700)
    )
    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 16f else 0f,
        animationSpec = tween(500)
    )
    val translationY by animateFloatAsState(
        targetValue = if (isHovered) (-8).dp.value else 0f,
        animationSpec = tween(500)
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundDark.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = PrimaryPurple.copy(alpha = 0.2f),
                spotColor = PrimaryPurple.copy(alpha = 0.1f)
            )
//            .graphicsLayer {
//                translationY = translationY
//            }
            .clip(RoundedCornerShape(24.dp))
            .clickable { onRocketClick(rocket.id) }
            .padding(1.dp) // Simula el borde
            .background(
                color = if (isHovered) PrimaryPurple.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(1.dp) // Ajuste fino para el efecto de borde
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // Simulación de "hover" solo con la elevación y el scale, ya que Compose no tiene un hover nativo de escritorio para móvil
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
                                    BackgroundDark.copy(alpha = 0.9f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                // Insignia de Estado
                RocketStatusBadge(
                    isActive = rocket.active,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
            }

            // Contenido de Texto y Estadísticas
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = rocket.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isHovered) PrimaryPurple else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = rocket.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGray,
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
                        value = "${rocket.height?.meters?.toInt() ?: "?"}m",
                        modifier = Modifier.weight(1f)
                    )
                    // Masa
                    StatItem(
                        icon = Icons.Default.Scale,
                        label = stringResource(R.string.stat_mass),
                        value = "${(rocket.mass?.kg?.toFloat() ?: 0f) / 1000f}t",
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
                        color = TextGray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.view_details),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryPurple
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = null,
                            tint = PrimaryPurple,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Insignia de estado (Active/Retired)
 */
@Composable
fun RocketStatusBadge(isActive: Boolean, modifier: Modifier = Modifier) {
    val (color, icon, text) = if (isActive) {
        Triple(Color(0xFF4CAF50), Icons.Default.CheckCircle, stringResource(R.string.status_active))
    } else {
        Triple(Color(0xFF9E9E9E), Icons.Default.Close, stringResource(R.string.status_retired))
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null, // Ícono decorativo
            tint = color,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Elemento de estadística individual
 */
@Composable
fun StatItem(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryPurple,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextGray
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun PreviewRocketCard() {
    // Necesitas un tema definido para que el preview se vea bien
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = PrimaryPurple,
            background = BackgroundDark,
            surface = BackgroundDark
        )
    ) {
        Surface(color = BackgroundDark) {
            RocketCard(
                rocket = Rocket(
                    id = "falcon9",
                    name = "Falcon 9",
                    description = "Falcon 9 is a reusable, two-stage rocket designed and manufactured by SpaceX.",
                    active = true,
                    costPerLaunch = 67000000,
                    firstFlight = "2010-06-04",
                    flickrImages = listOf("https://farm1.staticflickr.com/929/28787338307_3453a17f9e_b.jpg"),
                    height = Dimension(70.0f),
                    mass = Mass(549054)
                ),
                onRocketClick = {}
            )
        }
    }
}


// --- DATOS SIMULADOS (MOCK) ---

val mockRockets = listOf(
    Rocket(
        id = "falcon_9",
        name = "Falcon 9",
        description = "Cohete orbital reutilizable de dos etapas diseñado y fabricado por SpaceX.",
        active = true,
        costPerLaunch = 67000000, // 67 millones
        firstFlight = "2010-06-04",
        flickrImages = listOf("https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"),
        height = Dimension(70.0f),
        mass = Mass(549054) // 549 toneladas
    ),
    Rocket(
        id = "falcon_heavy",
        name = "Falcon Heavy",
        description = "El cohete operativo más poderoso del mundo, compuesto por tres núcleos Falcon 9.",
        active = true,
        costPerLaunch = 97000000, // 97 millones
        firstFlight = "2018-02-06",
        flickrImages = listOf("https://farm5.staticflickr.com/4599/38583829295_581f34dd84_b.jpg"),
        height = Dimension(70.0f),
        mass = Mass(1420788) // 1420 toneladas
    ),
    Rocket(
        id = "starship",
        name = "Starship",
        description = "Vehículo de lanzamiento de próxima generación totalmente reutilizable, diseñado para transportar humanos a Marte.",
        active = true,
        costPerLaunch = 2000000, // 2 millones (objetivo)
        firstFlight = "2023-04-20",
        flickrImages = listOf("https://farm8.staticflickr.com/7867/46571545625_753a80693a_b.jpg"),
        height = Dimension(120.0f),
        mass = Mass(5000000) // 5000 toneladas
    ),
    Rocket(
        id = "falcon_1",
        name = "Falcon 1",
        description = "Primer cohete de combustible líquido lanzado a órbita desarrollado por SpaceX. Retirado.",
        active = false,
        costPerLaunch = 6700000, // 6.7 millones
        firstFlight = "2006-03-24",
        flickrImages = listOf("https://images2.imgbox.com/44/1a/06j6chIz_o.png"),
        height = Dimension(22.25f),
        mass = Mass(38560) // 38 toneladas
    )
)