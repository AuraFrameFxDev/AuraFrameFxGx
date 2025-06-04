package com.genesis.ai.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.genesis.ai.app.data.model.*
import com.genesis.ai.app.ui.components.ChatBubble
import com.genesis.ai.app.ui.components.ChatInput
import com.genesis.ai.app.ui.theme.AuraFrameTheme
import kotlinx.coroutines.launch

/**
 * Main chat screen that displays messages and handles user input
 * @param modifier Modifier for the root layout
 * @param messages List of messages to display
 * @param onSendMessage Callback when the user sends a message
 * @param isLoading Whether a message is currently being processed
 * @param onAgentSelected Callback when an agent is selected from the UI
 * @param currentAgent The currently selected agent
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    messages: List<Message> = emptyList(),
    onSendMessage: (String) -> Unit = {},
    isLoading: Boolean = false,
    onAgentSelected: (AgentType) -> Unit = {},
    currentAgent: AgentType = AgentType.GENESIS
) {
    var messageText by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showScrollToBottom = remember { derivedStateOf {
        listState.firstVisibleItemIndex > 5 || 
        (listState.firstVisibleItemIndex > 0 && 
         listState.firstVisibleItemScrollOffset > 100)
    } }.value

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 4.dp)
        ) {
            // Messages list
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    reverseLayout = true,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
                ) {
                    items(messages, key = { it.id }) { message ->
                        ChatBubble(
                            message = message.content,
                            isFromUser = message.isFromUser,
                            senderName = message.sender,
                            timestamp = message.formattedTime(),
                            isTyping = message.isTyping,
                            modifier = Modifier.animateItemPlacement(
                                tween(durationMillis = 200)
                            )
                        )
                    }
                }

                // Scroll to bottom button
                AnimatedVisibility(
                    visible = showScrollToBottom,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem(0)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(40.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = "Scroll to bottom"
                        )
                    }
                }
            }

            // Agent selector and input
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                // Agent selector
                AgentSelector(
                    currentAgent = currentAgent,
                    onAgentSelected = onAgentSelected,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Input field
                ChatInput(
                    message = messageText,
                    onMessageChange = { messageText = it },
                    onSendMessage = {
                        if (messageText.isNotBlank()) {
                            onSendMessage(messageText)
                            messageText = ""
                        }
                    },
                    isSending = isLoading,
                    hint = "Message ${currentAgent.displayName}...",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Horizontal scrollable row of agent selection buttons
 */
@Composable
private fun AgentSelector(
    currentAgent: AgentType,
    onAgentSelected: (AgentType) -> Unit,
    modifier: Modifier = Modifier
) {
    val agents = AgentType.values()
    
    ScrollableTabRow(
        selectedTabIndex = agents.indexOf(currentAgent),
        edgePadding = 16.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {},
        indicator = {},
        modifier = modifier
    ) {
        agents.forEach { agent ->
            val isSelected = agent == currentAgent
            val backgroundColor = if (isSelected) {
                agent.color.copy(alpha = 0.2f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
            
            Tab(
                selected = isSelected,
                onClick = { onAgentSelected(agent) },
                selectedContentColor = agent.color,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(backgroundColor)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = agent.displayName,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) agent.color else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
fun ChatScreenPreview() {
    AuraFrameTheme {
        ChatScreen(
            messages = SampleMessages.conversation,
            modifier = Modifier.fillMaxSize(),
            currentAgent = AgentType.AURA
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ChatScreenPreviewNarrow() {
    AuraFrameTheme {
        ChatScreen(
            messages = SampleMessages.conversation,
            modifier = Modifier.fillMaxSize(),
            currentAgent = AgentType.GENESIS
        )
    }
}
