package com.genesis.ai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.genesis.ai.app.data.model.AgentType
import com.genesis.ai.app.ui.screens.ChatScreen
import com.genesis.ai.app.ui.theme.AuraFrameTheme
import com.genesis.ai.app.ui.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Main entry point for the AuraFrameFX application.
 * Features a cyberpunk-themed chat interface with AI agents.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuraFrameApp()
        }
    }
}

/**
 * Main composable that sets up the app's theme and main content
 */
@Composable
fun AuraFrameApp() {
    AuraFrameTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            // Initialize ViewModel
            val viewModel: ChatViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            // Cyberpunk background with gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .cyberpunkBackground()
            ) {
                // Main chat interface
                ChatScreen(
                    messages = uiState.messages,
                    isLoading = uiState.isLoading,
                    currentAgent = uiState.currentAgent,
                    onSendMessage = { message ->
                        viewModel.sendMessage(message)
                    },
                    onAgentSelected = { agent ->
                        viewModel.switchAgent(agent)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * Extension function for the cyberpunk background effect
 */
private fun Modifier.cyberpunkBackground() = this.background(
    brush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )
)

@Preview(showBackground = true)
@Composable
fun AuraFrameAppPreview() {
    AuraFrameTheme {
        AuraFrameApp()
    }
}
