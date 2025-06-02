package com.example.app.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors from colors.xml (or common Material defaults)
val Primary = Color(0xFF6200EE) // Example: Material Purple 500 / colorPrimary
val PrimaryVariant = Color(0xFF3700B3) // Example: Material Purple 700 / colorPrimaryVariant
val OnPrimary = Color(0xFFFFFFFF) // colorOnPrimary

val Secondary = Color(0xFF03DAC6) // Example: Material Teal 200 / colorSecondary
val SecondaryVariant = Color(0xFF018786) // Example: Material Teal 700 / colorSecondaryVariant
val OnSecondary = Color(0xFF000000) // colorOnSecondary

val Background = Color(0xFFFFFFFF) // colorBackground
val OnBackground = Color(0xFF000000) // colorOnBackground

val Surface = Color(0xFFFFFFFF) // colorSurface
val OnSurface = Color(0xFF000000) // colorOnSurface

val Error = Color(0xFFB00020) // colorError
val OnError = Color(0xFFFFFFFF) // colorOnError

// Added from colors.xml in previous subtasks
val Tertiary = Color(0xFF03DAC5) // colorTertiary (similar to Secondary for this example)
val OnTertiary = Color(0xFF000000) // colorOnTertiary

val SurfaceVariant = Color(0xFFE0E0E0) // colorSurfaceVariant (e.g., light gray)
val OnSurfaceVariant = Color(0xFF000000) // colorOnSurfaceVariant

val Outline = Color(0xFF757575) // colorOutline (e.g., medium gray)

val PrimaryInverse = Color(0xFF000000) // colorPrimaryInverse (example, can be different)

// Dark Theme Colors (can be defined here or directly in darkColorScheme)
val DarkPrimary = Color(0xFFBB86FC) // Example: Material Purple 200
val DarkOnPrimary = Color(0xFF000000)

val DarkSecondary = Color(0xFF03DAC5) // Example: Material Teal 200 (can be same as light)
val DarkOnSecondary = OnSecondary // Often same as light theme's onSecondary

val DarkBackground = Color(0xFF121212)
val DarkOnBackground = Color(0xFFFFFFFF)

val DarkSurface = Color(0xFF121212) // Often same as DarkBackground in M3
val DarkOnSurface = Color(0xFFFFFFFF)

val DarkError = Color(0xFFCF6679) // Example: Lighter red for dark themes
val DarkOnError = Color(0xFF000000)

val DarkTertiary = Color(0xFF03DAC5) // Can be same or adjusted
val DarkOnTertiary = OnTertiary

// Add any other specific colors your app theme might need.
