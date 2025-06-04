package com.genesis.ai.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = neonTeal,
    onPrimary = onPrimary,
    primaryContainer = neonBlue,
    onPrimaryContainer = onPrimary,
    secondary = neonPurple,
    onSecondary = onSecondary,
    secondaryContainer = neonPurple,
    onSecondaryContainer = onSecondary,
    tertiary = neonBlue,
    onTertiary = onTertiary,
    tertiaryContainer = neonBlue,
    onTertiaryContainer = onTertiary,
    background = darkBackground,
    onBackground = onSurface,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    outline = colorOutline,
    outlineVariant = colorOutline,
    surfaceTint = neonTeal,
    inverseSurface = colorSurfaceInverse,
    inverseOnSurface = onSurface,
    inversePrimary = neonTeal
)

private val LightColorScheme = darkColorScheme(
    // Use dark theme as base for both themes to maintain neon visibility
    primary = neonTeal,
    onPrimary = onPrimary,
    primaryContainer = neonBlue,
    onPrimaryContainer = onPrimary,
    secondary = neonPurple,
    onSecondary = onSecondary,
    secondaryContainer = neonPurple,
    onSecondaryContainer = onSecondary,
    tertiary = neonBlue,
    onTertiary = onTertiary,
    tertiaryContainer = neonBlue,
    onTertiaryContainer = onTertiary,
    background = darkBackground,
    onBackground = onSurface,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    outline = colorOutline,
    outlineVariant = colorOutline,
    surfaceTint = neonTeal,
    inverseSurface = colorSurfaceInverse,
    inverseOnSurface = onSurface,
    inversePrimary = neonTeal
)

@Composable
fun AuraFrameFXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
