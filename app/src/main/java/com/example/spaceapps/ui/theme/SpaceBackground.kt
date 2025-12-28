package com.example.spaceapps.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

/**
 * Gradient brush used across the app for backgrounds - adapts to theme
 */
val spaceGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(
            SpaceTheme.colors.backgroundGradientStart,
            SpaceTheme.colors.backgroundGradientMiddle,
            SpaceTheme.colors.backgroundGradientEnd
        )
    )

/**
 * Reusable space-themed background with decorative circles - adapts to theme
 */
@Composable
fun SpaceBackground(
    modifier: Modifier = Modifier,
    showDecorativeCircles: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (showDecorativeCircles) {
            // Bottom-left decorative circle
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .offset(x = (-80).dp, y = 520.dp)
                    .clip(CircleShape)
                    .background(SpaceTheme.colors.decorativeCirclePrimary)
            )
            // Top-right decorative circle
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .offset(x = 220.dp, y = (-40).dp)
                    .clip(CircleShape)
                    .background(SpaceTheme.colors.decorativeCircleSecondary)
            )
        }
        content()
    }
}

/**
 * Reusable space-themed background with gradient - adapts to theme
 */
@Composable
fun SpaceGradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(spaceGradient),
        content = content
    )
}
