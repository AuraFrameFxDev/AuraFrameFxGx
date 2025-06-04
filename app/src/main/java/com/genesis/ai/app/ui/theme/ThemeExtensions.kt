package com.genesis.ai.app.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Applies a neon glow effect to the composable
 */
fun Modifier.neonGlow(
    color: Color = Primary,
    alpha: Float = 0.5f,
    blurRadius: Dp = 8.dp,
    offset: Dp = 0.dp,
    shape: Shape = RoundedCornerShape(8.dp)
): Modifier = this.then(
    graphicsLayer {
        this.shape = shape
        clip = true
        compositingStrategy = androidx.compose.ui.graphics.compositingStrategy
    }.drawWithCache {
        val brush = Brush.radialGradient(
            colors = listOf(
                color.copy(alpha = alpha),
                color.copy(alpha = 0f)
            ),
            radius = blurRadius.toPx(),
            center = Offset(0f, 0f)
        )
        
        onDrawWithContent {
            drawContent()
            drawRect(
                brush = brush,
                blendMode = BlendMode.Screen
            )
        }
    }
)

/**
 * Creates a cyberpunk-styled card with glow effect
 */
fun Modifier.cyberpunkCard(
    color: Color = SurfaceVariant,
    glowColor: Color = Primary.copy(alpha = 0.3f),
    shape: Shape = RoundedCornerShape(12.dp),
    padding: PaddingValues = PaddingValues(16.dp)
): Modifier = this.then(
    clip(shape)
        .background(color)
        .padding(padding)
        .neonGlow(glowColor, shape = shape)
)

/**
 * Extension function for adding a cyberpunk-style divider
 */
@Composable
fun BoxScope.CyberpunkDivider(
    modifier: Modifier = Modifier,
    color: Color = Primary.copy(alpha = 0.5f),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    androidx.compose.material3.Divider(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = startIndent)
            .align(androidx.compose.ui.Alignment.BottomStart),
        color = color,
        thickness = thickness
    )
}

/**
 * Creates a gradient background with cyberpunk colors
 */
fun Modifier.cyberpunkGradient(
    colors: List<Color> = listOf(
        Primary.copy(alpha = 0.1f),
        Secondary.copy(alpha = 0.05f),
        Tertiary.copy(alpha = 0.1f)
    ),
    angle: Float = 135f
): Modifier = this.background(
    Brush.linearGradient(
        colors = colors,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
)

/**
 * Creates a cyberpunk-style button modifier
 */
fun Modifier.cyberpunkButton(
    isEnabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp)
): Modifier = this.then(
    clip(shape)
        .background(
            when {
                isEnabled -> Brush.horizontalGradient(
                    colors = listOf(Primary, Secondary)
                )
                else -> Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                )
            }
        )
        .neonGlow(
            color = if (isEnabled) Primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            alpha = if (isEnabled) 0.5f else 0.2f,
            shape = shape
        )
)
