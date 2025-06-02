package com.example.app.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Colors from user's colors.xml
val NeonTeal = Color(0xFF00FFCC)
val NeonPurple = Color(0xFFFF00FF)
val NeonBlue = Color(0xFF00FFFF)

// Background Colors
val DarkBackground = Color(0xFF1A1A2E) // dark_background
val Surface = Color(0xFF2D2D3E)       // surface
val SurfaceVariant = Color(0xFF3A3A52) // surface_variant

// Text Colors
val OnSurface = Color(0xFFFFFFFF)       // on_surface
val OnSurfaceVariant = Color(0xFFD0D0D0) // on_surface_variant
val OnPrimary = Color(0xFF000000)         // on_primary (for text on NeonTeal button)

// Error and other colors from XML
val ErrorColor = Color(0xFFFF3B30) // error
val WarningColor = Color(0xFFFF9500) // warning
val SuccessColor = Color(0xFF34C759) // success

// Define a light palette as well, even if the theme is primarily dark.
// These can be adjusted later but provide a fallback.
val LightPrimary = Color(0xFF007A6E) // Darker Teal for light theme primary
val LightOnPrimary = Color(0xFFFFFFFF)
val LightSecondary = Color(0xFF7A007A) // Darker Purple
val LightOnSecondary = Color(0xFFFFFFFF)
val LightTertiary = Color(0xFF007A7A) // Darker Cyan/Blue
val LightOnTertiary = Color(0xFFFFFFFF)

val LightBackground = Color(0xFFF0F0F8) // A light background (e.g., off-white/light lavender)
val LightOnBackground = Color(0xFF1C1B1F) // Standard dark text for light backgrounds
val LightSurface = Color(0xFFF8F8FC) // Slightly different from background for cards, etc.
val LightOnSurface = Color(0xFF1C1B1F)

val LightError = Color(0xFFB00020) // Standard Material error for light themes
val LightOnError = Color(0xFFFFFFFF)

// Other colors from XML that might be useful in Compose
val Accent1 = NeonTeal
val Accent2 = NeonPurple
val Accent3 = NeonBlue
