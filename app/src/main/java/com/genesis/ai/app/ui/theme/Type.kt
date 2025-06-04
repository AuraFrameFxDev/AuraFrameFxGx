package com.genesis.ai.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

/**
 * Cyberpunk typography styles for AuraFrameFX
 * Features a futuristic, high-tech feel with glowing text effects
 */

// Base typography with Material 3 defaults
private val BaseTypography = Typography()

// Custom text styles with cyberpunk theme
val Typography: Typography
    @Composable
    get() {
        val colors = AuraFrameColors
        
        return remember(colors) {
            Typography(
                // Display styles
                displayLarge = BaseTypography.displayLarge.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = colors.neonTeal,
                    textShadow = LocalTextShadow.current.primary
                ),
                
                displayMedium = BaseTypography.displayMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = colors.neonPink,
                    textShadow = LocalTextShadow.current.secondary
                ),
                
                displaySmall = BaseTypography.displaySmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = colors.neonCyan,
                    textShadow = LocalTextShadow.current.tertiary
                ),
                
                // Headline styles
                headlineLarge = BaseTypography.headlineLarge.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = colors.offWhite,
                    letterSpacing = 0.5.sp
                ),
                
                headlineMedium = BaseTypography.headlineMedium.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.offWhite,
                    letterSpacing = 0.5.sp
                ),
                
                headlineSmall = BaseTypography.headlineSmall.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    color = colors.offWhite,
                    letterSpacing = 0.5.sp
                ),
                
                // Title styles
                titleLarge = BaseTypography.titleLarge.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = colors.neonTeal,
                    letterSpacing = 0.sp
                ),
                
                titleMedium = BaseTypography.titleMedium.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.offWhite,
                    letterSpacing = 0.15.sp
                ),
                
                titleSmall = BaseTypography.titleSmall.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    color = colors.offWhite.copy(alpha = 0.8f),
                    letterSpacing = 0.1.sp
                ),
                
                // Body styles
                bodyLarge = BaseTypography.bodyLarge.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = colors.offWhite,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp
                ),
                
                bodyMedium = BaseTypography.bodyMedium.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = colors.offWhite.copy(alpha = 0.9f),
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp
                ),
                
                bodySmall = BaseTypography.bodySmall.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = colors.offWhite.copy(alpha = 0.7f),
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp
                ),
                
                // Label styles
                labelLarge = BaseTypography.labelLarge.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    color = colors.neonPink,
                    letterSpacing = 0.1.sp
                ),
                
                labelMedium = BaseTypography.labelMedium.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    color = colors.neonCyan,
                    letterSpacing = 0.5.sp
                ),
                
                labelSmall = BaseTypography.labelSmall.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    color = colors.offWhite.copy(alpha = 0.6f),
                    letterSpacing = 0.5.sp
                )
            )
        }
    }

// Custom text styles for specific UI elements
@Composable
fun Typography.codeBlockStyle(): TextStyle {
    return TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = AuraFrameColors.neonCyan,
        background = AuraFrameColors.darkGray.copy(alpha = 0.8f),
        letterSpacing = 0.sp
    )
}

@Composable
fun Typography.chatMessageStyle(isFromUser: Boolean = false): TextStyle {
    return if (isFromUser) {
        bodyLarge.copy(
            color = AuraFrameColors.neonTeal,
            fontWeight = FontWeight.Medium
        )
    } else {
        bodyLarge.copy(
            color = AuraFrameColors.offWhite,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun Typography.chatTimestampStyle(): TextStyle {
    return labelSmall.copy(
        color = AuraFrameColors.offWhite.copy(alpha = 0.6f)
    )
}

@Composable
fun Typography.chatSenderStyle(): TextStyle {
    return labelMedium.copy(
        color = AuraFrameColors.neonPink,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun Typography.buttonStyle(): TextStyle {
    return labelLarge.copy(
        color = AuraFrameColors.darkerGray,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
}

@Composable
fun Typography.inputFieldStyle(): TextStyle {
    return bodyLarge.copy(
        color = AuraFrameColors.offWhite,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun Typography.errorTextStyle(): TextStyle {
    return bodySmall.copy(
        color = MaterialTheme.colorScheme.error,
        fontWeight = FontWeight.Medium
    )
}

// Text shadow for neon glow effect
data class TextShadow(
    val primary: androidx.compose.ui.graphics.Shadow,
    val secondary: androidx.compose.ui.graphics.Shadow,
    val tertiary: androidx.compose.ui.graphics.Shadow
)

private val LocalTextShadow = staticCompositionLocalOf {
    TextShadow(
        primary = androidx.compose.ui.graphics.Shadow(
            color = AuraFrameColorPalette().neonTeal.copy(alpha = 0.7f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 16f
        ),
        secondary = androidx.compose.ui.graphics.Shadow(
            color = AuraFrameColorPalette().neonPink.copy(alpha = 0.7f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 16f
        ),
        tertiary = androidx.compose.ui.graphics.Shadow(
            color = AuraFrameColorPalette().neonCyan.copy(alpha = 0.7f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 16f
        )
    )
}

@Composable
fun rememberTextShadow(
    primary: Color = AuraFrameColors.neonTeal,
    secondary: Color = AuraFrameColors.neonPink,
    tertiary: Color = AuraFrameColors.neonCyan,
    alpha: Float = 0.7f,
    blurRadius: Float = 16f
): TextShadow {
    val colors = AuraFrameColors
    
    return remember(colors, primary, secondary, tertiary, alpha, blurRadius) {
        TextShadow(
            primary = androidx.compose.ui.graphics.Shadow(
                color = primary.copy(alpha = alpha),
                offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                blurRadius = blurRadius
            ),
            secondary = androidx.compose.ui.graphics.Shadow(
                color = secondary.copy(alpha = alpha),
                offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                blurRadius = blurRadius
            ),
            tertiary = androidx.compose.ui.graphics.Shadow(
                color = tertiary.copy(alpha = alpha),
                offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                blurRadius = blurRadius
            )
        )
    }
}
