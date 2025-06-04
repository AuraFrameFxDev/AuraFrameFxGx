package com.genesis.ai.app.ui.theme

import androidx.compose.ui.graphics.Color

package com.genesis.ai.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Cyberpunk Neon Noir Color Palette
 * A high-contrast theme with teal, purple, and pink neon accents on dark backgrounds
 */

// Primary Colors - Neon Accents
val NeonTeal = Color(0xFF00FFCC)     // Primary teal accent
val NeonPurple = Color(0xFFA020F0)    // Vibrant purple
val NeonPink = Color(0xFFFF00FF)      // Electric pink
val NeonCyan = Color(0xFF00FFFF)      // Bright cyan

// Background Colors - Deep Noir
val Background = Color(0xFF000000)    // Pure black
val Surface = Color(0xFF0A0A0A)       // Slightly off-black for surfaces
val SurfaceVariant = Color(0xFF1E1E1E) // Dark gray for elevated surfaces
val CardBackground = Color(0xFF121212) // Card background

// Text Colors - Glowing
val OnBackground = Color(0xFFFFFFFF)  // White for primary text
val OnSurface = Color(0xFFE0E0E0)     // Light gray for secondary text
val OnPrimary = Color(0xFF000000)     // Black text on neon elements
val OnSecondary = Color(0xFF000000)   // Black text on secondary elements

// Accent Colors - Glowing Effects
val Primary = NeonTeal                // Main accent color
val Secondary = NeonPurple            // Secondary accent
val Tertiary = NeonPink               // Tertiary accent
val Quaternary = NeonCyan             // Quaternary accent

// Status Colors
val Error = Color(0xFFFF3B30)         // Red for errors
val Warning = Color(0xFFFFCC00)        // Yellow for warnings
val Success = Color(0xFF34C759)        // Green for success
val Info = NeonCyan                   // Cyan for info

// Special Effects
val GlowOverlay = Color(0x3300FFCC)   // Teal glow effect
val DarkOverlay = Color(0x80000000)   // Dark overlay for modals

// Gradients - For special UI elements
val PrimaryGradient = listOf(NeonTeal, NeonCyan)
val SecondaryGradient = listOf(NeonPurple, NeonPink)
val BackgroundGradient = listOf(Background, Surface)
val PulseOverlay = Color(0x1AE000FF) // Semi-transparent purple pulse
val HoverOverlay = Color(0x1A00FFFF) // Semi-transparent cyan hover
val PressOverlay = Color(0x1AFF00FF) // Semi-transparent pink press
