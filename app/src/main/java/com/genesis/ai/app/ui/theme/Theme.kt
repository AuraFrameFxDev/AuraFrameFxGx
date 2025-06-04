package com.genesis.ai.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Cyberpunk Neon Color Palette
private val NeonTeal = Color(0xFF00F0FF)
private val NeonPink = Color(0xFFFF00FF)
private val NeonPurple = Color(0xFF9D00FF)
private val NeonCyan = Color(0xFF00FFF0)
private val DarkGray = Color(0xFF121212)
private val DarkerGray = Color(0xFF0A0A0A)
private val LightGray = Color(0xFF2D2D2D)
private val OffWhite = Color(0xFFF0F0F0)

// Cyberpunk color scheme
private val DarkColorScheme = darkColorScheme(
    primary = NeonTeal,
    onPrimary = Color.Black,
    primaryContainer = NeonTeal.copy(alpha = 0.2f),
    onPrimaryContainer = NeonTeal,
    
    secondary = NeonPink,
    onSecondary = Color.Black,
    secondaryContainer = NeonPink.copy(alpha = 0.2f),
    onSecondaryContainer = NeonPink,
    
    tertiary = NeonPurple,
    onTertiary = Color.White,
    tertiaryContainer = NeonPurple.copy(alpha = 0.2f),
    onTertiaryContainer = NeonPurple,
    
    background = DarkerGray,
    onBackground = OffWhite,
    surface = DarkGray,
    onSurface = OffWhite,
    
    surfaceVariant = LightGray,
    onSurfaceVariant = OffWhite.copy(alpha = 0.8f),
    
    error = Color(0xFFFF5252),
    onError = Color.Black,
    errorContainer = Color(0x66FF5252),
    onErrorContainer = Color(0xFFFFCDD2),
    
    outline = LightGray,
    outlineVariant = LightGray.copy(alpha = 0.5f)
)

// Light color scheme (not used in this app, but provided for completeness)
private val LightColorScheme = lightColorScheme(
    primary = NeonTeal,
    onPrimary = Color.White,
    primaryContainer = NeonTeal.copy(alpha = 0.2f),
    onPrimaryContainer = NeonTeal,
    
    secondary = NeonPink,
    onSecondary = Color.White,
    secondaryContainer = NeonPink.copy(alpha = 0.2f),
    onSecondaryContainer = NeonPink,
    
    tertiary = NeonPurple,
    onTertiary = Color.White,
    tertiaryContainer = NeonPurple.copy(alpha = 0.2f),
    onTertiaryContainer = NeonPurple,
    
    background = Color.White,
    onBackground = Color.Black,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black,
    
    surfaceVariant = Color(0xFFEEEEEE),
    onSurfaceVariant = Color(0xFF444444),
    
    error = Color(0xFFD32F2F),
    onError = Color.White,
    errorContainer = Color(0xFFFFCDD2),
    onErrorContainer = Color(0xB71C1C),
    
    outline = Color(0xFF888888),
    outlineVariant = Color(0xFFCCCCCC)
)

// Custom colors for the app
val AuraFrameColors
    @Composable
    get() = LocalAuraFrameColors.current

private val LocalAuraFrameColors = staticCompositionLocalOf {
    AuraFrameColorPalette()
}

data class AuraFrameColorPalette(
    val neonTeal: Color = NeonTeal,
    val neonPink: Color = NeonPink,
    val neonPurple: Color = NeonPurple,
    val neonCyan: Color = NeonCyan,
    val darkGray: Color = DarkGray,
    val darkerGray: Color = DarkerGray,
    val lightGray: Color = LightGray,
    val offWhite: Color = OffWhite
)

@Composable
fun AuraFrameTheme(
    darkTheme: Boolean = true, // Always use dark theme for cyberpunk
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicDarkColorScheme(context)
        }
        else -> DarkColorScheme
    }

    // Provide custom colors for easy access
    val colors = AuraFrameColors(
        primary = colorScheme.primary,
        secondary = colorScheme.secondary,
        tertiary = colorScheme.tertiary,
        background = colorScheme.background,
        surface = colorScheme.surface,
        onPrimary = colorScheme.onPrimary,
        onSecondary = colorScheme.onSecondary,
        onBackground = colorScheme.onBackground,
        onSurface = colorScheme.onSurface,
        error = colorScheme.error,
        onError = colorScheme.onError
    )

    // Provide the theme to the composition
    CompositionLocalProvider(
        LocalAuraFrameColors provides colors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AuraFrameTypography,
            content = content
        )
    }
}

/**
 * Custom colors for AuraFrame theme
 */
data class AuraFrameColors(
    val primary: androidx.compose.ui.graphics.Color,
    val secondary: androidx.compose.ui.graphics.Color,
    val tertiary: androidx.compose.ui.graphics.Color,
    val background: androidx.compose.ui.graphics.Color,
    val surface: androidx.compose.ui.graphics.Color,
    val onPrimary: androidx.compose.ui.graphics.Color,
    val onSecondary: androidx.compose.ui.graphics.Color,
    val onBackground: androidx.compose.ui.graphics.Color,
    val onSurface: androidx.compose.ui.graphics.Color,
    val error: androidx.compose.ui.graphics.Color,
    val onError: androidx.compose.ui.graphics.Color
)

/**
 * CompositionLocal for AuraFrame colors
 */
val LocalAuraFrameColors = staticCompositionLocalOf {
    AuraFrameColors(
        primary = Primary,
        secondary = Secondary,
        tertiary = Tertiary,
        background = Background,
        surface = Surface,
        onPrimary = OnPrimary,
        onSecondary = OnSecondary,
        onBackground = OnBackground,
        onSurface = OnSurface,
        error = Error,
        onError = OnPrimary
    )
}
