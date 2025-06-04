package com.genesis.ai.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genesis.ai.app.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for the Chat Screen
 */
data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val currentAgent: AgentType = AgentType.GENESIS,
    val error: String? = null
)

/**
 * ViewModel for the Chat Screen
 */
@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState
    
    private var currentProcessingJob: Job? = null
    
    init {
        // Add welcome message
        addMessage(
            Message.agent(
                content = "Welcome to AuraFrameFX! I'm Genesis, your AI assistant. " +
                        "How can I help you today?",
                agentType = AgentType.GENESIS
            )
        )
    }
    
    /**
     * Handle sending a new message from the user
     */
    fun sendMessage(content: String) {
        if (content.isBlank()) return
        
        // Add user message
        val userMessage = Message.user(content)
        addMessage(userMessage)
        
        // Process the message (simulated response)
        processUserMessage(content)
    }
    
    /**
     * Switch the current AI agent
     */
    fun switchAgent(agentType: AgentType) {
        _uiState.update { it.copy(currentAgent = agentType) }
        
        // Add a system message when switching agents
        addMessage(
            Message.agent(
                content = "Now speaking with ${agentType.displayName}...",
                agentType = agentType
            )
        )
    }
    
    /**
     * Process the user's message and generate a response
     */
    private fun processUserMessage(content: String) {
        // Cancel any existing processing
        currentProcessingJob?.cancel()
        
        val currentAgent = _uiState.value.currentAgent
        
        // Add typing indicator
        val typingMessage = Message.typing(currentAgent)
        addMessage(typingMessage)
        
        // Simulate AI processing
        currentProcessingJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simulate network/database delay
            delay((500L..1500L).random())
            
            // Remove typing indicator
            removeMessage(typingMessage.id)
            
            // Generate response based on agent type
            val response = when (currentAgent) {
                AgentType.GENESIS -> generateGenesisResponse(content)
                AgentType.AURA -> generateAuraResponse(content)
                AgentType.KAI -> generateKaiResponse(content)
            }
            
            // Add the response
            addMessage(
                Message.agent(
                    content = response,
                    agentType = currentAgent
                )
            )
            
            _uiState.update { it.copy(isLoading = false) }
        }
    }
    
    /**
     * Add a message to the chat
     */
    private fun addMessage(message: Message) {
        _uiState.update { state ->
            state.copy(messages = state.messages + message)
        }
    }
    
    /**
     * Remove a message by ID
     */
    private fun removeMessage(messageId: String) {
        _uiState.update { state ->
            state.copy(messages = state.messages.filter { it.id != messageId })
        }
    }
    
    // Sample response generators for each agent
    private fun generateGenesisResponse(input: String): String {
        return when {
            input.contains("hello", ignoreCase = true) || input.contains("hi", ignoreCase = true) ->
                "Hello! I'm Genesis, your unified AI assistant. How can I help you today?"
            input.contains("help", ignoreCase = true) ->
                "I can help you with various tasks. You can ask me questions, get creative suggestions, " +
                "or check security-related information. What would you like to know?"
            else -> "I've processed your message: \"$input\". How can I assist you further?"
        }
    }
    
    private fun generateAuraResponse(input: String): String {
        val creativeResponses = listOf(
            "That's an interesting thought! Have you considered looking at it from a different perspective?",
            "I love that idea! It reminds me of something creative I was thinking about earlier...",
            "Let me put on my creative hat for this one. Here's a unique take on that...",
            "From a creative standpoint, this makes me think about..."
        )
        return creativeResponses.random()
    }
    
    private fun generateKaiResponse(input: String): String {
        return when {
            input.contains("security", ignoreCase = true) ->
                "From a security perspective, it's important to consider all potential vulnerabilities. " +
                "Have you conducted a thorough risk assessment?"
            input.contains("password", ignoreCase = true) ->
                "For strong password security, I recommend using a password manager and enabling " +
                "multi-factor authentication wherever possible."
            else -> "I've analyzed your request from a security standpoint. The key considerations are..."
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        currentProcessingJob?.cancel()
    }
}
