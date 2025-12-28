package com.example.spaceapps.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * SpaceX Theme Colors - Complete color palette for dark and light modes
 */
object SpaceColors {
    // Background colors (Dark mode)
    val BackgroundDark = Color(0xFF0F0F23)
    val BackgroundGradientStart = Color(0xFF1A1A2E)
    val BackgroundGradientMiddle = Color(0xFF16213E)
    val BackgroundGradientEnd = Color(0xFF0F0F23)

    // Background colors (Light mode)
    val BackgroundLight = Color(0xFFF5F5F8)
    val BackgroundLightGradientStart = Color(0xFFE8E8F0)
    val BackgroundLightGradientMiddle = Color(0xFFF0F0F8)
    val BackgroundLightGradientEnd = Color(0xFFF5F5F8)

    // Primary accent colors
    val Primary = Color(0xFF6750A4)
    val PrimaryLight = Color(0xFF9A82DB)
    val PrimaryDark = Color(0xFF5A4494)
    val PrimaryContainer = Color(0xFFEADDFF)
    val OnPrimaryContainer = Color(0xFF21005D)

    // Secondary colors
    val Secondary = Color(0xFF625B71)
    val SecondaryLight = Color(0xFFCCC2DC)
    val SecondaryContainer = Color(0xFFE8DEF8)
    val OnSecondaryContainer = Color(0xFF1D192B)

    // Tertiary colors
    val Tertiary = Color(0xFF7D5260)
    val TertiaryLight = Color(0xFFEFB8C8)
    val TertiaryContainer = Color(0xFFFFD8E4)
    val OnTertiaryContainer = Color(0xFF31111D)

    // Text colors (Dark mode)
    val TextPrimary = Color.White
    val TextSecondary = Color(0xFFB3B3C5)
    val TextMuted = Color(0xFF9AA0AD)
    val TextLabel = Color(0xFF9A9AB5)

    // Text colors (Light mode)
    val TextPrimaryLight = Color(0xFF1C1B1F)
    val TextSecondaryLight = Color(0xFF49454F)
    val TextMutedLight = Color(0xFF79747E)
    val TextLabelLight = Color(0xFF49454F)

    // Surface colors (Dark mode)
    val SurfaceCard = Color(0xFF1E1E32)
    val SurfaceOverlay = Color(0x1AFFFFFF)
    val SurfaceVariantAlpha = 0.25f
    val SurfaceDark = Color(0xFF1C1B1F)
    val OnSurfaceDark = Color(0xFFE6E1E5)
    val SurfaceVariantDark = Color(0xFF49454F)
    val OnSurfaceVariantDark = Color(0xFFCAC4D0)

    // Surface colors (Light mode)
    val SurfaceLight = Color(0xFFFFFBFE)
    val OnSurfaceLight = Color(0xFF1C1B1F)
    val SurfaceVariantLight = Color(0xFFE7E0EC)
    val OnSurfaceVariantLight = Color(0xFF49454F)
    val SurfaceCardLight = Color(0xFFFFFFFF)
    val SurfaceOverlayLight = Color(0x1A000000)

    // Status colors
    val StatusActiveBackground = Color(0xFF2E7D32).copy(alpha = 0.18f)
    val StatusActiveBorder = Color(0xFF66BB6A).copy(alpha = 0.45f)
    val StatusActiveText = Color(0xFF81C784)
    val StatusActiveBackgroundLight = Color(0xFF2E7D32).copy(alpha = 0.12f)
    val StatusActiveTextLight = Color(0xFF2E7D32)

    val StatusRetiredBackground = Color(0xFF6B7280).copy(alpha = 0.18f)
    val StatusRetiredBorder = Color(0xFF9CA3AF).copy(alpha = 0.45f)
    val StatusRetiredText = Color(0xFF9CA3AF)
    val StatusRetiredBackgroundLight = Color(0xFF6B7280).copy(alpha = 0.12f)
    val StatusRetiredTextLight = Color(0xFF6B7280)

    // Decorative circles
    val DecorativeCirclePrimary = Color(0xFF6750A4).copy(alpha = 0.085f)
    val DecorativeCircleSecondary = Color(0xFF9A82DB).copy(alpha = 0.08f)
    val DecorativeCirclePrimaryLight = Color(0xFF6750A4).copy(alpha = 0.06f)
    val DecorativeCircleSecondaryLight = Color(0xFF9A82DB).copy(alpha = 0.05f)

    // Icon colors
    val IconMuted = Color(0xFF6B7280)
    val IconLight = Color(0xFFBFC3CC)
    val IconMutedLight = Color(0xFF79747E)
    val IconLightMode = Color(0xFF49454F)

    // TopAppBar
    val TopAppBarBackground = Color(0xFF1A1A2E).copy(alpha = 0.88f)
    val TopAppBarBackgroundLight = Color(0xFFF5F5F8).copy(alpha = 0.95f)

    // Error colors
    val Error = Color(0xFFB3261E)
    val ErrorDark = Color(0xFFF2B8B5)
    val ErrorContainer = Color(0xFFF9DEDC)
    val OnErrorContainer = Color(0xFF410E0B)

    // Outline colors
    val OutlineDark = Color(0xFF938F99)
    val OutlineLight = Color(0xFF79747E)
    val OutlineVariantDark = Color(0xFF49454F)
    val OutlineVariantLight = Color(0xFFCAC4D0)
}
