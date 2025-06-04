package com.genesis.ai.app.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.genesis.ai.app.ui.theme.AuraFrameTheme
import com.genesis.ai.app.ui.viewmodel.AiUiState
import com.genesis.ai.app.ui.viewmodel.Message

@Composable
fun ChatUI(
    messages: List<Message>,
    uiState: AiUiState,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AuraFrameTheme.colors.background)
    ) {
        // Messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(
                    message = message,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        
        // Loading/Error indicator
        when (val state = uiState) {
            is AiUiState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
                Text(
                    text = state.message,
                    style = AuraFrameTheme.typography.bodySmall,
                    color = AuraFrameTheme.colors.onBackground,
                    modifier = Modifier.padding(8.dp)
                )
            }
            is AiUiState.Error -> {
                Text(
                    text = state.message,
                    style = AuraFrameTheme.typography.bodySmall,
                    color = AuraFrameTheme.colors.error,
                    modifier = Modifier.padding(8.dp)
                )
            }
            else -> {}
        }
        
        // Input area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = {
                    Text(
                        "Type a message...",
                        style = AuraFrameTheme.typography.bodyMedium,
                        color = AuraFrameTheme.colors.onBackground.copy(alpha = 0.5f)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = AuraFrameTheme.colors.surface,
                    textColor = AuraFrameTheme.colors.onSurface,
                    cursorColor = AuraFrameTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        onSendMessage(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AuraFrameTheme.colors.primary),
                enabled = inputText.isNotBlank() && uiState !is AiUiState.Loading
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = AuraFrameTheme.colors.onPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    val isFromUser = message.isFromUser
    val backgroundColor = if (isFromUser) {
        AuraFrameTheme.colors.primary
    } else if (message.isSystem) {
        AuraFrameTheme.colors.secondary
    } else {
        AuraFrameTheme.colors.surface
    }
    
    val textColor = when {
        isFromUser -> AuraFrameTheme.colors.onPrimary
        message.isSystem -> AuraFrameTheme.colors.onSecondary
        else -> AuraFrameTheme.colors.onSurface
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        contentAlignment = if (isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = message,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { if (isFromUser) it else -it }
                ) + fadeIn() with
                slideOutHorizontally(
                    targetOffsetX = { if (isFromUser) -it else it }
                ) + fadeOut()
            }
        ) { _ ->
            Surface(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isFromUser) 16.dp else 4.dp,
                            bottomEnd = if (isFromUser) 4.dp else 16.dp
                        )
                    ),
                color = backgroundColor,
                shadowElevation = 2.dp
            ) {
                Text(
                    text = message.text,
                    style = AuraFrameTheme.typography.bodyMedium,
                    color = textColor,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatUIPreview() {
    AuraFrameTheme {
        val messages = listOf(
            Message("Hello! How can I help you today?", false),
            Message("I need some creative ideas", true),
            Message("I'd be happy to help! What kind of ideas are you looking for?", false)
        )
        
        ChatUI(
            messages = messages,
            uiState = AiUiState.Ready,
            onSendMessage = {}
        )
    }
}
