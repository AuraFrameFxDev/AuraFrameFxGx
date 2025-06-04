package com.genesis.ai.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Material3 shape system for consistent corner styles
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Custom shape definitions for specific components
object AuraShapes {
    // Buttons
    val button = RoundedCornerShape(12.dp)

    // Cards
    val card = RoundedCornerShape(16.dp)
    val cardElevated = RoundedCornerShape(20.dp)

    // Dialogs
    val dialog = RoundedCornerShape(28.dp)

    // Chips
    val chip = RoundedCornerShape(50)

    // Input fields
    val textField = RoundedCornerShape(12.dp)

    // Navigation bar
    val bottomBar = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)

    // Badges
    val badge = RoundedCornerShape(50)
}
