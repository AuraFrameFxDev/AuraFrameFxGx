package com.genesis.ai.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genesis.ai.app.ai.core.Agent
import com.genesis.ai.app.ai.core.AgentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing AI agent interactions
 */
@HiltViewModel
class AiViewModel @Inject constructor(
    private val agent: Agent
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<AiUiState>(AiUiState.Initial)
    val uiState: StateFlow<AiUiState> = _uiState.asStateFlow()
    
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    
    init {
        initializeAgents()
    }
    
    private fun initializeAgents() {
        viewModelScope.launch {
            try {
                _uiState.value = AiUiState.Loading("Initializing AI...")
                agent.initialize()
                _uiState.value = AiUiState.Ready
                addSystemMessage("AI system initialized. How can I assist you today?")
            } catch (e: Exception) {
                _uiState.value = AiUiState.Error("Failed to initialize AI: ${e.message}")
            }
        }
    }
    
    fun processUserInput(input: String) {
        if (input.isBlank()) return
        
        viewModelScope.launch {
            try {
                // Add user message
                addUserMessage(input)
                
                // Process with agent
                _uiState.value = AiUiState.Loading("Processing...")
                val response = agent.process(input)
                
                // Add agent response
                addAgentMessage(response)
                _uiState.value = AiUiState.Ready
                
            } catch (e: Exception) {
                _uiState.value = AiUiState.Error("Error: ${e.message}")
                addSystemMessage("Sorry, I encountered an error: ${e.message}")
            }
        }
    }
    
    private fun addUserMessage(text: String) {
        _messages.value = _messages.value + Message(
            text = text,
            isFromUser = true,
            timestamp = System.currentTimeMillis()
        )
    }
    
    private fun addAgentMessage(text: String) {
        _messages.value = _messages.value + Message(
            text = text,
            isFromUser = false,
            timestamp = System.currentTimeMillis()
        )
    }
    
    private fun addSystemMessage(text: String) {
        _messages.value = _messages.value + Message(
            text = text,
            isFromUser = false,
            isSystem = true,
            timestamp = System.currentTimeMillis()
        )
    }
    
    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            agent.shutdown()
        }
    }
}

/**
 * UI state for the AI interaction screen
 */
sealed class AiUiState {
    object Initial : AiUiState()
    data class Loading(val message: String) : AiUiState()
    object Ready : AiUiState()
    data class Error(val message: String) : AiUiState()
}

/**
 * Represents a message in the chat
 */
data class Message(
    val text: String,
    val isFromUser: Boolean,
    val isSystem: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
