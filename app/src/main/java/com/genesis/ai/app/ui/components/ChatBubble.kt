package com.genesis.ai.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.genesis.ai.app.ui.theme.*

@Composable
fun ChatBubble(
    message: String,
    isFromUser: Boolean,
    senderName: String? = null,
    timestamp: String = "",
    isTyping: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bubbleColor = if (isFromUser) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val textColor = if (isFromUser) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    val alignment = if (isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        // Sender name (for AI agents)
        if (!isFromUser && !senderName.isNullOrEmpty()) {
            Text(
                text = senderName,
                style = MaterialTheme.typography.labelMedium,
                color = when (senderName.lowercase()) {
                    "genesis" -> Primary
                    "aura" -> Secondary
                    "kai" -> Tertiary
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        
        // Message bubble
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isFromUser) 16.dp else 4.dp,
                        bottomEnd = if (isFromUser) 4.dp else 16.dp
                    )
                )
                .background(bubbleColor)
                .neonGlow(
                    color = if (isFromUser) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    alpha = 0.3f
                )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                if (isTyping) {
                    TypingIndicator()
                } else {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                        modifier = Modifier.padding(4.dp)
                    )
                }
                
                // Timestamp
                Text(
                    text = timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun TypingIndicator() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Dot(delay = 0)
        Spacer(modifier = Modifier.width(4.dp))
        Dot(delay = 150)
        Spacer(modifier = Modifier.width(4.dp))
        Dot(delay = 300)
    }
}

@Composable
private fun Dot(delay: Int) {
    val alpha = remember { androidx.compose.animation.core.Animatable(0.3f) }
    
    LaunchedEffect(Unit) {
        while (true) {
            alpha.animateTo(1f, animationSpec = tween(300, delayMillis = delay))
            alpha.animateTo(0.3f, animationSpec = tween(300, delayMillis = 700))
        }
    }
    
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha.value))
    )
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    AuraFrameTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // User message
            ChatBubble(
                message = "Hello, Aura! How are you today?",
                isFromUser = true,
                timestamp = "12:34 PM"
            )
            
            // AI response
            ChatBubble(
                message = "Hello! I'm doing great, thanks for asking! How can I assist you today?",
                isFromUser = false,
                senderName = "Aura",
                timestamp = "12:35 PM"
            )
            
            // Typing indicator
            ChatBubble(
                message = "",
                isFromUser = false,
                senderName = "Genesis",
                isTyping = true,
                timestamp = "12:36 PM"
            )
        }
    }
}
