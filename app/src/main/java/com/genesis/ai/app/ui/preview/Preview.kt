package com.genesis.ai.app.ui.preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.genesis.ai.app.ui.components.*
import com.genesis.ai.app.ui.theme.AuraFrameTheme

/**
 * Preview component that wraps content with the AuraFrame theme and background
 */
@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AuraFrameTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
            color = androidx.compose.ui.graphics.Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .cyberpunkBackground()
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}

/**
 * Extension function for the cyberpunk background effect
 */
private fun Modifier.cyberpunkBackground() = this.background(
    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
        colors = listOf(
            androidx.compose.material3.MaterialTheme.colorScheme.background,
            androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )
)

/**
 * Preview for the chat message component
 */
@Preview(showBackground = true)
@Composable
fun PreviewChatMessage() {
    PreviewBox {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            // User message
            ChatMessage(
                message = "Hello, Aura! How are you today?",
                isFromUser = true,
                timestamp = "12:34 PM"
            )
            
            // AI response
            ChatMessage(
                message = "Hello! I'm doing great, thanks for asking! How can I assist you today?",
                isFromUser = false,
                senderName = "Aura",
                timestamp = "12:35 PM"
            )
        }
    }
}

/**
 * Preview for the chat input component
 */
@Preview(showBackground = true)
@Composable
fun PreviewChatInput() {
    PreviewBox {
        ChatInput(
            message = "",
            onMessageChange = {},
            onSendMessage = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Preview for the chat screen
 */
@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
fun PreviewChatScreen() {
    PreviewBox {
        ChatScreen(
            modifier = Modifier.fillMaxSize(),
            messages = listOf(
                ChatMessage(
                    message = "Welcome to AuraFrameFX!",
                    isFromUser = false,
                    senderName = "Genesis",
                    timestamp = "12:30 PM"
                ),
                ChatMessage(
                    message = "How can I help you today?",
                    isFromUser = false,
                    senderName = "Aura",
                    timestamp = "12:30 PM"
                )
            ),
            onSendMessage = {},
            isLoading = false
        )
    }
}
