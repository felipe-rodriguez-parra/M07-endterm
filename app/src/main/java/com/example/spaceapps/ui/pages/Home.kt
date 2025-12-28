package com.example.spaceapps.ui.pages

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spaceapps.R
import com.example.spaceapps.ui.components.RocketCard
import com.example.spaceapps.ui.viewmodel.RocketListViewModel
import com.example.spaceapps.ui.viewmodel.RocketListViewModelFactory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalContext
import com.example.spaceapps.domain.model.Rocket
import com.example.spaceapps.ui.theme.SpaceAppsTheme
import com.example.spaceapps.ui.theme.SpaceBackground
import com.example.spaceapps.ui.theme.SpaceColors
import com.example.spaceapps.ui.theme.SpaceTheme
import com.example.spaceapps.ui.viewmodel.RocketListUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onRocketClick: (String) -> Unit, onLogout: () -> Unit) {
    val context = LocalContext.current
    val app = (context.applicationContext as? Application)
    val viewModel: RocketListViewModel? = app?.let {
        androidx.lifecycle.viewmodel.compose.viewModel(
            factory = RocketListViewModelFactory(it)
        )
    }

    val state by viewModel?.state?.collectAsStateWithLifecycle() ?: remember {
        mutableStateOf(
            RocketListUiState(
                isLoading = false,
                error = null,
                query = "",
                rockets = emptyList()
            )
        )
    }
    val searchQuery = state.query
    val filtered = state.rockets.filter { r ->
        r.name.contains(searchQuery, ignoreCase = true) || r.description.contains(searchQuery, ignoreCase = true)
    }

    val colors = SpaceTheme.colors

    SpaceBackground {
        Column {
            // Header
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(8.dp))
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
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = colors.iconLight)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.topAppBarBackground
                )
            )

            // Content
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(20.dp))

                // Hero
                Column(modifier = Modifier.padding(bottom = 20.dp)) {
                    Box {
                        Text(
                            text = "Explore Rockets",
                            color = colors.textPrimary,
                            fontSize = 34.sp,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Discover the incredible fleet of SpaceX rockets that are revolutionizing space exploration.",
                        color = colors.textMuted,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Search
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel?.onQueryChange(it) },
                    placeholder = { Text(stringResource(R.string.search_rocket_placeholder), color = colors.textMuted) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = colors.textMuted)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colors.surfaceOverlay,
                        unfocusedContainerColor = colors.surfaceOverlay,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary,
                        focusedPlaceholderColor = colors.textMuted,
                        unfocusedPlaceholderColor = colors.textMuted,
                        focusedLeadingIconColor = colors.textMuted,
                        unfocusedLeadingIconColor = colors.textMuted
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                when {
                    state.isLoading && state.rockets.isEmpty() -> {
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Loading rockets...", color = colors.textMuted)
                        }
                    }

                    state.error != null && state.rockets.isEmpty() -> {
                        ErrorState(message = state.error ?: stringResource(id = R.string.action_retry), onRetry = { viewModel?.refresh() })
                    }

                    filtered.isEmpty() -> {
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = colors.iconMuted, modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(stringResource(R.string.empty_results_message), color = colors.textMuted)
                        }
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 220.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            itemsIndexed(filtered) { _, rocket ->
                                RocketCard(
                                    rocket = rocket.toUi(),
                                    onRocketClick = onRocketClick,
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
fun ErrorState(message: String, onRetry: () -> Unit) {
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
        Button(onClick = onRetry) {
            Text(stringResource(R.string.action_retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    SpaceAppsTheme {
        Home(onRocketClick = {}, onLogout = {})
    }
}

private fun Rocket.toUi(): Rocket =
    Rocket(
        id = id,
        name = name,
        description = description,
        active = active,
        costPerLaunch = costPerLaunch,
        firstFlight = firstFlight,
        flickrImages = flickrImages.ifEmpty { listOf("https://images.unsplash.com/photo-1516849841032-87cbac4d88f7?w=800") },
        heightMeters = heightMeters,
        massKg = massKg
    )
