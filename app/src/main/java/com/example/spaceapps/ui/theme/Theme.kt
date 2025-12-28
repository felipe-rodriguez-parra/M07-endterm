package com.example.spaceapps.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Dark color scheme using SpaceColors
 */
private val SpaceDarkColorScheme = darkColorScheme(
    primary = SpaceColors.PrimaryLight,
    onPrimary = Color.White,
    primaryContainer = SpaceColors.PrimaryDark,
    onPrimaryContainer = SpaceColors.PrimaryLight,
    secondary = SpaceColors.SecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = SpaceColors.Secondary,
    onSecondaryContainer = SpaceColors.SecondaryLight,
    tertiary = SpaceColors.TertiaryLight,
    onTertiary = Color.White,
    tertiaryContainer = SpaceColors.Tertiary,
    onTertiaryContainer = SpaceColors.TertiaryLight,
    background = SpaceColors.BackgroundDark,
    onBackground = SpaceColors.TextPrimary,
    surface = SpaceColors.SurfaceDark,
    onSurface = SpaceColors.OnSurfaceDark,
    surfaceVariant = SpaceColors.SurfaceVariantDark,
    onSurfaceVariant = SpaceColors.OnSurfaceVariantDark,
    error = SpaceColors.ErrorDark,
    onError = Color.Black,
    errorContainer = SpaceColors.Error,
    onErrorContainer = SpaceColors.ErrorDark,
    outline = SpaceColors.OutlineDark,
    outlineVariant = SpaceColors.OutlineVariantDark
)

/**
 * Light color scheme using SpaceColors
 */
private val SpaceLightColorScheme = lightColorScheme(
    primary = SpaceColors.Primary,
    onPrimary = Color.White,
    primaryContainer = SpaceColors.PrimaryContainer,
    onPrimaryContainer = SpaceColors.OnPrimaryContainer,
    secondary = SpaceColors.Secondary,
    onSecondary = Color.White,
    secondaryContainer = SpaceColors.SecondaryContainer,
    onSecondaryContainer = SpaceColors.OnSecondaryContainer,
    tertiary = SpaceColors.Tertiary,
    onTertiary = Color.White,
    tertiaryContainer = SpaceColors.TertiaryContainer,
    onTertiaryContainer = SpaceColors.OnTertiaryContainer,
    background = SpaceColors.BackgroundLight,
    onBackground = SpaceColors.TextPrimaryLight,
    surface = SpaceColors.SurfaceLight,
    onSurface = SpaceColors.OnSurfaceLight,
    surfaceVariant = SpaceColors.SurfaceVariantLight,
    onSurfaceVariant = SpaceColors.OnSurfaceVariantLight,
    error = SpaceColors.Error,
    onError = Color.White,
    errorContainer = SpaceColors.ErrorContainer,
    onErrorContainer = SpaceColors.OnErrorContainer,
    outline = SpaceColors.OutlineLight,
    outlineVariant = SpaceColors.OutlineVariantLight
)

/**
 * Extended colors for custom UI elements that are not part of Material3 ColorScheme
 */
data class SpaceExtendedColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val textLabel: Color,
    val surfaceCard: Color,
    val surfaceOverlay: Color,
    val iconMuted: Color,
    val iconLight: Color,
    val topAppBarBackground: Color,
    val decorativeCirclePrimary: Color,
    val decorativeCircleSecondary: Color,
    val statusActiveBackground: Color,
    val statusActiveBorder: Color,
    val statusActiveText: Color,
    val statusRetiredBackground: Color,
    val statusRetiredBorder: Color,
    val statusRetiredText: Color,
    val backgroundGradientStart: Color,
    val backgroundGradientMiddle: Color,
    val backgroundGradientEnd: Color
)

val LocalSpaceColors = staticCompositionLocalOf {
    SpaceExtendedColors(
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textMuted = Color.Unspecified,
        textLabel = Color.Unspecified,
        surfaceCard = Color.Unspecified,
        surfaceOverlay = Color.Unspecified,
        iconMuted = Color.Unspecified,
        iconLight = Color.Unspecified,
        topAppBarBackground = Color.Unspecified,
        decorativeCirclePrimary = Color.Unspecified,
        decorativeCircleSecondary = Color.Unspecified,
        statusActiveBackground = Color.Unspecified,
        statusActiveBorder = Color.Unspecified,
        statusActiveText = Color.Unspecified,
        statusRetiredBackground = Color.Unspecified,
        statusRetiredBorder = Color.Unspecified,
        statusRetiredText = Color.Unspecified,
        backgroundGradientStart = Color.Unspecified,
        backgroundGradientMiddle = Color.Unspecified,
        backgroundGradientEnd = Color.Unspecified
    )
}

private val DarkExtendedColors = SpaceExtendedColors(
    textPrimary = SpaceColors.TextPrimary,
    textSecondary = SpaceColors.TextSecondary,
    textMuted = SpaceColors.TextMuted,
    textLabel = SpaceColors.TextLabel,
    surfaceCard = SpaceColors.SurfaceCard,
    surfaceOverlay = SpaceColors.SurfaceOverlay,
    iconMuted = SpaceColors.IconMuted,
    iconLight = SpaceColors.IconLight,
    topAppBarBackground = SpaceColors.TopAppBarBackground,
    decorativeCirclePrimary = SpaceColors.DecorativeCirclePrimary,
    decorativeCircleSecondary = SpaceColors.DecorativeCircleSecondary,
    statusActiveBackground = SpaceColors.StatusActiveBackground,
    statusActiveBorder = SpaceColors.StatusActiveBorder,
    statusActiveText = SpaceColors.StatusActiveText,
    statusRetiredBackground = SpaceColors.StatusRetiredBackground,
    statusRetiredBorder = SpaceColors.StatusRetiredBorder,
    statusRetiredText = SpaceColors.StatusRetiredText,
    backgroundGradientStart = SpaceColors.BackgroundGradientStart,
    backgroundGradientMiddle = SpaceColors.BackgroundGradientMiddle,
    backgroundGradientEnd = SpaceColors.BackgroundGradientEnd
)

private val LightExtendedColors = SpaceExtendedColors(
    textPrimary = SpaceColors.TextPrimaryLight,
    textSecondary = SpaceColors.TextSecondaryLight,
    textMuted = SpaceColors.TextMutedLight,
    textLabel = SpaceColors.TextLabelLight,
    surfaceCard = SpaceColors.SurfaceCardLight,
    surfaceOverlay = SpaceColors.SurfaceOverlayLight,
    iconMuted = SpaceColors.IconMutedLight,
    iconLight = SpaceColors.IconLightMode,
    topAppBarBackground = SpaceColors.TopAppBarBackgroundLight,
    decorativeCirclePrimary = SpaceColors.DecorativeCirclePrimaryLight,
    decorativeCircleSecondary = SpaceColors.DecorativeCircleSecondaryLight,
    statusActiveBackground = SpaceColors.StatusActiveBackgroundLight,
    statusActiveBorder = SpaceColors.StatusActiveBorder,
    statusActiveText = SpaceColors.StatusActiveTextLight,
    statusRetiredBackground = SpaceColors.StatusRetiredBackgroundLight,
    statusRetiredBorder = SpaceColors.StatusRetiredBorder,
    statusRetiredText = SpaceColors.StatusRetiredTextLight,
    backgroundGradientStart = SpaceColors.BackgroundLightGradientStart,
    backgroundGradientMiddle = SpaceColors.BackgroundLightGradientMiddle,
    backgroundGradientEnd = SpaceColors.BackgroundLightGradientEnd
)

@Composable
fun SpaceAppsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to use our custom SpaceX colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> SpaceDarkColorScheme
        else -> SpaceLightColorScheme
    }

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalSpaceColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

/**
 * Access extended space colors from anywhere in the app
 */
object SpaceTheme {
    val colors: SpaceExtendedColors
        @Composable
        get() = LocalSpaceColors.current
}
