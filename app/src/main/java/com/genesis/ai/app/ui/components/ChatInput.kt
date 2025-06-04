package com.genesis.ai.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.genesis.ai.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatInput(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Message Aura...",
    isSending: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val scope = rememberCoroutineScope()
    
    // Pulsing animation for the input field when focused
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    val isSendButtonEnabled = message.isNotBlank() && !isSending
    val buttonColor = animateColorAsState(
        targetValue = if (isSendButtonEnabled) {
            AuraFrameColors.neonTeal
        } else {
            AuraFrameColors.darkGray.copy(alpha = 0.5f)
        },
        label = "buttonColor",
        animationSpec = tween(durationMillis = 300)
    )
    
    val borderColor = if (isFocused) {
        AuraFrameColors.neonTeal
    } else {
        AuraFrameColors.darkGray
    }
    
    // Animate the border width based on focus
    val borderWidth = animateDpAsState(
        targetValue = if (isFocused) 1.5.dp else 1.dp,
        label = "borderWidth"
    )
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        // Message input field with cyberpunk styling
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = AuraFrameColors.darkGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = borderWidth.value,
                    color = borderColor.copy(alpha = if (isFocused) 0.8f else 0.3f),
                    shape = RoundedCornerShape(24.dp)
                )
                .drawWithCache {
                    val glowColor = AuraFrameColors.neonTeal.copy(alpha = pulse * 0.2f)
                    onDrawWithContent {
                        drawContent()
                        if (isFocused) {
                            drawRoundRect(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        glowColor.copy(alpha = 0f),
                                        glowColor,
                                        glowColor.copy(alpha = 0f)
                                    ),
                                    startX = 0f,
                                    endX = size.width * pulse
                                ),
                                topLeft = Offset(0f, 0f),
                                size = size,
                                blendMode = BlendMode.Plus,
                                alpha = 0.5f
                            )
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = message,
                onValueChange = onMessageChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = AuraFrameColors.neonTeal,
                    focusedIndicator = Color.Transparent,
                    unfocusedIndicator = Color.Transparent,
                    disabledIndicator = Color.Transparent,
                    errorIndicator = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = AuraFrameColors.offWhite.copy(alpha = 0.5f)
                        )
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = AuraFrameColors.offWhite
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = false,
                maxLines = 5,
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    autoCorrect = true
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (isSendButtonEnabled) {
                            onSendMessage()
                            keyboardController?.hide()
                        }
                    }
                ),
                enabled = !isSending
            )
        }
        
        // Send button with cyberpunk styling
        Spacer(modifier = Modifier.width(12.dp))
        
        // Animated send button with pulsing effect when active
        val buttonSize = 48.dp
        val buttonGlowAlpha = if (isSendButtonEnabled) 0.7f else 0.2f
        
        Box(
            modifier = Modifier
                .size(buttonSize)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            buttonColor.value.copy(alpha = if (isSendButtonEnabled) 0.8f else 0.3f),
                            buttonColor.value.copy(alpha = if (isSendButtonEnabled) 0.4f else 0.1f)
                        )
                    ),
                    shape = CircleShape
                )
                .neonGlow(
                    color = buttonColor.value,
                    alpha = if (isSendButtonEnabled) 0.7f else 0.2f,
                    shape = CircleShape,
                    blurRadius = if (isSendButtonEnabled) 12.dp else 4.dp
                )
                .clickable(
                    enabled = isSendButtonEnabled,
                    onClick = {
                        scope.launch {
                            // Add a small delay to show the click effect
                            onSendMessage()
                            keyboardController?.hide()
                        }
                    }
                )
            contentAlignment = Alignment.Center
        ) {
            // Animated send icon
            val iconTint = if (isSendButtonEnabled) {
                AuraFrameColors.darkerGray
            } else {
                AuraFrameColors.offWhite.copy(alpha = 0.5f)
            }
            
            // Add a subtle rotation animation when sending
            val rotation by animateFloatAsState(
                targetValue = if (isSending) 360f else 0f,
                label = "iconRotation"
            )
            
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = iconTint,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation)
            )
            
            // Add a subtle pulse effect when enabled
            if (isSendButtonEnabled) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    buttonColor.value.copy(alpha = 0.3f * pulse),
                                    buttonColor.value.copy(alpha = 0f)
                                )
                            ),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatInputPreview() {
    AuraFrameTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AuraFrameColors.darkerGray)
                .padding(16.dp)
        ) {
            var message1 by remember { mutableStateOf("") }
            var message2 by remember { mutableStateOf("Hello, Aura!") }
            
            // Normal state
            Text(
                text = "Normal State:",
                style = MaterialTheme.typography.labelLarge,
                color = AuraFrameColors.neonPink,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ChatInput(
                message = message1,
                onMessageChange = { message1 = it },
                onSendMessage = { /* Handle send */ },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Focused state
            Text(
                text = "Focused with Text:",
                style = MaterialTheme.typography.labelLarge,
                color = AuraFrameColors.neonTeal,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ChatInput(
                message = message2,
                onMessageChange = { message2 = it },
                onSendMessage = { /* Handle send */ },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sending state
            Text(
                text = "Sending State:",
                style = MaterialTheme.typography.labelLarge,
                color = AuraFrameColors.neonCyan,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ChatInput(
                message = "Sending message...",
                onMessageChange = {},
                onSendMessage = {},
                isSending = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
