package com.example.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Dark Color Scheme using the new "Retro Neon Cyberpunk" palette
private val AuraDarkColorScheme = darkColorScheme(
    primary = NeonTeal,
    onPrimary = OnPrimary, // Black text on NeonTeal buttons/primary elements
    primaryContainer = NeonBlue, // As per user's themes.xml
    onPrimaryContainer = OnPrimary, // As per user's themes.xml

    secondary = NeonPurple,
    onSecondary = OnSurface, // White text on NeonPurple (assuming OnSurface is white/light)
    secondaryContainer = NeonPurple, // As per user's themes.xml
    onSecondaryContainer = OnSurface, // As per user's themes.xml

    tertiary = NeonBlue,
    onTertiary = OnSurface, // White text on NeonBlue
    tertiaryContainer = NeonBlue, // As per user's themes.xml
    onTertiaryContainer = OnSurface, // As per user's themes.xml

    background = DarkBackground,
    onBackground = OnSurface, // Main text color for dark theme (e.g., White or light neon)

    surface = Surface,
    onSurface = OnSurface, // Text on surfaces

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    outline = NeonPurple, // Example for outlines, can be tuned

    error = ErrorColor, // from Color.kt, based on colors.xml
    onError = OnPrimary, // Black text on error color, assuming error color is light enough
    errorContainer = ErrorColor, // Consistent with themes.xml
    onErrorContainer = OnPrimary // Consistent with themes.xml
)

// Light Color Scheme (fallback, can be refined)
private val AuraLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimary, // Or a lighter variant
    onPrimaryContainer = LightOnPrimary,

    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondary,
    onSecondaryContainer = LightOnSecondary,

    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiary,
    onTertiaryContainer = LightOnTertiary,

    background = LightBackground,
    onBackground = LightOnBackground,

    surface = LightSurface,
    onSurface = LightOnSurface,

    surfaceVariant = LightSurface, // Or a slightly different light gray
    onSurfaceVariant = LightOnBackground, // Text on surface variant

    outline = LightSecondary, // Example

    error = LightError,
    onError = LightOnError,
    errorContainer = LightError, // Adjust if needed
    onErrorContainer = LightOnError // Adjust if needed
)

@Composable
fun AuraFrameFXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic coloring is disabled by default to enforce the cyberpunk theme.
    // Set to true to allow Material You dynamic colors on Android S+ if desired.
    dynamicColor: Boolean = false, 
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Always use AuraDarkColorScheme if darkTheme is true, regardless of dynamicColor setting,
        // to strongly enforce the cyberpunk aesthetic for dark mode.
        darkTheme -> AuraDarkColorScheme
        // If dynamicColor is enabled and on Android S+, use dynamic light colors.
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicLightColorScheme(context) 
        }
        // Otherwise, use the predefined AuraLightColorScheme.
        else -> AuraLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // From Typography.kt
        // shapes = AppShapes, // TODO: Define AppShapes in Shapes.kt if custom shapes are needed
        content = content
    )
}
