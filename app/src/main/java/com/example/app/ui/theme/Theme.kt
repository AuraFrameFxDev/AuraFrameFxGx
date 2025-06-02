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

// Using color names defined in Color.kt
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary, // Using specific dark theme primary from Color.kt
    onPrimary = DarkOnPrimary,
    primaryContainer = PrimaryVariant, // Often a darker shade for dark themes
    // onPrimaryContainer = OnPrimary, // Adjust as needed

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    // secondaryContainer = SecondaryVariant, // Adjust as needed
    // onSecondaryContainer = OnSecondary, // Adjust as needed

    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    // tertiaryContainer = Tertiary, // Adjust as needed
    // onTertiaryContainer = OnTertiary, // Adjust as needed

    error = DarkError,
    onError = DarkOnError,
    // errorContainer = Error, // Adjust as needed
    // onErrorContainer = OnError, // Adjust as needed

    background = DarkBackground,
    onBackground = DarkOnBackground,

    surface = DarkSurface,
    onSurface = DarkOnSurface,

    surfaceVariant = SurfaceVariant, // Can be shared or adjusted
    onSurfaceVariant = OnSurfaceVariant, // Can be shared or adjusted
    
    outline = Outline // Can be shared or adjusted
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant, // Or a lighter shade of primary
    // onPrimaryContainer = OnPrimary, // Adjust as needed

    secondary = Secondary,
    onSecondary = OnSecondary,
    // secondaryContainer = SecondaryVariant, // Adjust as needed
    // onSecondaryContainer = OnSecondary, // Adjust as needed

    tertiary = Tertiary,
    onTertiary = OnTertiary,
    // tertiaryContainer = Tertiary, // Adjust as needed
    // onTertiaryContainer = OnTertiary, // Adjust as needed

    error = Error,
    onError = OnError,
    // errorContainer = Error, // Adjust as needed
    // onErrorContainer = OnError, // Adjust as needed

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    outline = Outline

    /* Other default colors can be left to Material3 defaults or explicitly set. */
)

@Composable
fun AuraFrameFXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic coloring is available on Android S+
    dynamicColor: Boolean = true, // Set to false if you don't want dynamic color
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // From Typography.kt
        // shapes = AppShapes, // TODO: Define AppShapes in Shapes.kt if needed
        content = content
    )
}
