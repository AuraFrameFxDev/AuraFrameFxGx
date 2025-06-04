package com.genesis.ai.app.ui.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Applies a cyberpunk-style neon glow effect to a composable
 */
fun Modifier.neonGlow(
    color: Color = MaterialTheme.colorScheme.primary,
    borderWidth: Float = 1f,
    glowRadius: Float = 10f,
    alpha: Float = 0.7f,
): Modifier = composed {
    val glowColor = color.copy(alpha = alpha)

    this.then(
        drawWithCache {
            val fillBrush = Brush.radialGradient(
                colors = listOf(
                    glowColor.copy(alpha = 0.7f),
                    glowColor.copy(alpha = 0.3f),
                    glowColor.copy(alpha = 0f)
                ),
                radius = glowRadius * density,
                center = Offset.Zero
            )

            val borderBrush = Brush.linearGradient(
                colors = listOf(
                    glowColor,
                    glowColor.copy(alpha = 0.7f),
                    glowColor
                )
            )

            onDrawWithContent {
                drawContent()

                // Draw glow
                drawRect(
                    brush = fillBrush,
                    topLeft = Offset(-glowRadius / 2, -glowRadius / 2),
                    size = size.copy(
                        width = size.width + glowRadius,
                        height = size.height + glowRadius
                    ),
                    blendMode = BlendMode.Screen
                )

                // Draw border
                drawRect(
                    brush = borderBrush,
                    topLeft = Offset.Zero,
                    size = size,
                    style = Stroke(width = borderWidth),
                    blendMode = BlendMode.Screen
                )
            }
        }
    )
}

/**
 * Applies a pulsing neon effect to a composable
 */
@Composable
fun Modifier.pulsingNeon(
    color: Color = MaterialTheme.colorScheme.primary,
    minAlpha: Float = 0.3f,
    maxAlpha: Float = 0.9f,
    duration: Int = 2000,
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = maxAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    this.then(
        Modifier.drawWithCache {
            val brush = Brush.linearGradient(
                colors = listOf(
                    color.copy(alpha = alpha),
                    color.copy(alpha = alpha * 0.7f),
                    color.copy(alpha = alpha)
                )
            )

            onDrawWithContent {
                drawContent()
                drawRect(
                    brush = brush,
                    blendMode = BlendMode.Screen,
                    topLeft = Offset.Zero,
                    size = size
                )
            }
        }
    )
}

/**
 * Applies a scanline effect to a composable
 */
@Composable
fun Modifier.scanlineEffect(
    lineColor: Color = Color.Green.copy(alpha = 0.1f),
    lineHeight: Float = 2f,
    speed: Int = 2000,
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(speed, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    this.then(
        Modifier.drawWithCache {
            val lineBrush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    lineColor,
                    lineColor,
                    Color.Transparent
                ),
                startY = 0f,
                endY = lineHeight * 2
            )

            onDrawWithContent {
                drawContent()

                // Draw moving scanline
                val y = offsetY * (size.height + lineHeight * 2) - lineHeight
                drawRect(
                    brush = lineBrush,
                    topLeft = Offset(0f, y),
                    size = size.copy(height = lineHeight),
                    blendMode = BlendMode.Screen
                )
            }
        }
    )
}

/**
 * Applies a cyberpunk-style grid background
 */
fun Modifier.cyberGrid(
    gridColor: Color = Color.Green.copy(alpha = 0.1f),
    lineWidth: Float = 1f,
    cellSize: Float = 40f,
): Modifier = composed {
    this.then(
        Modifier.drawWithCache {
            val gridPath = Path().apply {
                // Vertical lines
                var x = 0f
                while (x < size.width) {
                    moveTo(x, 0f)
                    lineTo(x, size.height)
                    x += cellSize
                }

                // Horizontal lines
                var y = 0f
                while (y < size.height) {
                    moveTo(0f, y)
                    lineTo(size.width, y)
                    y += cellSize
                }
            }

            onDrawWithContent {
                drawContent()
                drawPath(
                    path = gridPath,
                    color = gridColor,
                    style = Stroke(width = lineWidth),
                    blendMode = BlendMode.Screen
                )
            }
        }
    )
}
