package com.example.spaceapps.ui.pages

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.spaceapps.R
import com.example.spaceapps.domain.model.Rocket
import com.example.spaceapps.ui.theme.SpaceAppsTheme
import com.example.spaceapps.ui.theme.SpaceColors
import com.example.spaceapps.ui.theme.SpaceGradientBackground
import com.example.spaceapps.ui.theme.SpaceTheme
import com.example.spaceapps.ui.theme.spaceGradient
import com.example.spaceapps.ui.viewmodel.RocketDetailUiState
import com.example.spaceapps.ui.viewmodel.RocketDetailViewModel
import com.example.spaceapps.ui.viewmodel.RocketDetailViewModelFactory
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.core.net.toUri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.mutableIntStateOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SpaceRocket(rocketId: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel: RocketDetailViewModel = viewModel(
        factory = RocketDetailViewModelFactory(context.applicationContext as Application, rocketId)
    )
    val state by viewModel.state.collectAsState()
    val colors = SpaceTheme.colors

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Space",
                            color = colors.textPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "X",
                            color = SpaceColors.PrimaryLight,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = " • ${stringResource(R.string.rocket_detail_title)}",
                            color = colors.textMuted,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = colors.iconLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.topAppBarBackground
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        when (val uiState = state) {
            RocketDetailUiState.Loading -> {
                SpaceGradientBackground(modifier = Modifier.padding(paddingValues)) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Loading rocket details…", color = colors.textMuted)
                        }
                    }
                }
            }
            is RocketDetailUiState.Error -> {
                SpaceGradientBackground(modifier = Modifier.padding(paddingValues)) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = colors.textMuted,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = uiState.message, color = colors.textMuted, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = onBack) { Text(text = stringResource(id = R.string.back)) }
                        }
                    }
                }
            }
            is RocketDetailUiState.Success -> {
                RocketDetailContent(
                    rocket = uiState.rocket,
                    paddingValues = paddingValues,
                    onWikipediaClick = { name ->
                        val url = "https://en.wikipedia.org/wiki/${name.replace(" ", "_")}"
                        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RocketDetailContent(
    rocket: Rocket,
    paddingValues: PaddingValues,
    onWikipediaClick: (String) -> Unit
) {
    val images = rocket.flickrImages.ifEmpty { listOf("https://images.unsplash.com/photo-1516849841032-87cbac4d88f7?w=800") }
    val (currentIndex, setIndex) = rememberSaveable { mutableIntStateOf(0) }
    val colors = SpaceTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(spaceGradient)
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Gallery
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(colors.surfaceCard.copy(alpha = 0.2f))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = images[currentIndex.coerceIn(images.indices)]),
                    contentDescription = stringResource(R.string.rocket_image_description, rocket.name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (images.size > 1) {
                    IconButton(
                        onClick = { setIndex(if (currentIndex == 0) images.lastIndex else currentIndex - 1) },
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.CenterStart)
                            .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(50))
                    ) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = stringResource(id = R.string.back), tint = colors.textPrimary)
                    }
                    IconButton(
                        onClick = { setIndex(if (currentIndex == images.lastIndex) 0 else currentIndex + 1) },
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.CenterEnd)
                            .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(50))
                    ) {
                        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "Next", tint = colors.textPrimary)
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        images.forEachIndexed { index, _ ->
                            val isSelected = index == currentIndex
                            Box(
                                modifier = Modifier
                                    .height(8.dp)
                                    .width(if (isSelected) 22.dp else 8.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        if (isSelected) colors.textPrimary else colors.textPrimary.copy(alpha = 0.6f)
                                    )
                                    .clickable { setIndex(index) }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Info
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                StatusBadge(active = rocket.active)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = rocket.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = rocket.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.textSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onWikipediaClick(rocket.name) },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.button_more_info_wikipedia))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stats grid
            val stats = listOf(
                RocketStat("Height", rocket.heightMeters?.let { "${"%.1f".format(it)} m" } ?: "N/A", rocket.heightMeters?.let { "${"%.1f".format(it * 3.28084)} ft" } ?: ""),
                RocketStat("Mass", rocket.massKg?.let { "${"%.0f".format(it / 1000f)} t" } ?: "N/A", rocket.massKg?.let { "${"%,d".format(it)} kg" } ?: ""),
                RocketStat("Cost per launch", if (rocket.costPerLaunch > 0) "$${(rocket.costPerLaunch / 1_000_000)}M" else "N/A", "USD"),
                RocketStat("First flight", rocket.firstFlight, ""),
                RocketStat("Status", if (rocket.active) "Active" else "Retired", "")
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 2
            ) {
                stats.forEach { stat ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 120.dp)
                    ) {
                        StatCard(stat)
                    }
                }
            }
        }
    }
}

private data class RocketStat(val label: String, val value: String, val subLabel: String)

@Composable
private fun StatCard(stat: RocketStat) {
    val colors = SpaceTheme.colors
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = colors.surfaceCard.copy(alpha = SpaceColors.SurfaceVariantAlpha)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stat.label.uppercase(), style = MaterialTheme.typography.labelSmall, color = colors.textLabel)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = stat.value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = colors.textPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stat.subLabel.ifBlank { " " },
                style = MaterialTheme.typography.labelMedium,
                color = colors.textLabel
            )
        }
    }
}

@Composable
private fun StatusBadge(active: Boolean) {
    val colors = SpaceTheme.colors
    val background = if (active) colors.statusActiveBackground else colors.statusRetiredBackground
    val border = if (active) colors.statusActiveBorder else colors.statusRetiredBorder
    val textColor = if (active) colors.statusActiveText else colors.statusRetiredText
    Surface(
        shape = RoundedCornerShape(50),
        color = background,
        border = BorderStroke(1.dp, border)
    ) {
        Text(
            text = if (active) stringResource(R.string.status_active) else stringResource(R.string.status_retired),
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpaceRocket() {
    SpaceAppsTheme {
        RocketDetailContent(
            rocket = Rocket(
                id = "falcon9",
                name = "Falcon 9",
                description = "Reusable two-stage rocket designed and manufactured by SpaceX.",
                active = true,
                costPerLaunch = 67000000,
                firstFlight = "2010-06-04",
                flickrImages = listOf("https://farm1.staticflickr.com/929/28787338307_3453a17f9e_b.jpg", "https://images.unsplash.com/photo-1516849841032-87cbac4d88f7?w=800"),
                heightMeters = 70.0,
                massKg = 549054
            ),
            paddingValues = PaddingValues(),
            onWikipediaClick = {}
        )
    }
}