package com.genesis.ai.app.ai.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Genesis is the central AI agent that orchestrates communication between
 * Aura (creative) and Kai (security) agents.
 */
@Singleton
class GenesisAgent @Inject constructor(
    private val auraAgent: AuraAgent,
    private val kaiAgent: KaiAgent
) : Agent {
    override val id: String = "genesis"
    override val name: String = "Genesis"
    
    private val _status = MutableStateFlow(AgentStatus.CREATED)
    override val status: StateFlow<AgentStatus> = _status.asStateFlow()
    
    private var isInitialized = false
    
    override suspend fun initialize() {
        if (isInitialized) return
        
        try {
            _status.value = AgentStatus.INITIALIZING
            
            // Initialize sub-agents
            auraAgent.initialize()
            kaiAgent.initialize()
            
            isInitialized = true
            _status.value = AgentStatus.READY
        } catch (e: Exception) {
            _status.value = AgentStatus.ERROR
            throw AgentException.InitializationError("Failed to initialize Genesis agent", e)
        }
    }
    
    override suspend fun process(input: String): String {
        if (!isInitialized) {
            throw AgentException.ProcessingError("Agent not initialized")
        }
        
        return try {
            _status.value = AgentStatus.PROCESSING
            
            // Simple intent detection - in a real app, use ML for better classification
            when {
                // Security-related queries go to Kai
                input.contains("security", ignoreCase = true) || 
                input.contains("protect", ignoreCase = true) ||
                input.contains("threat", ignoreCase = true) -> {
                    kaiAgent.process(input)
                }
                // Creative tasks go to Aura
                else -> {
                    auraAgent.process(input)
                }
            }
        } catch (e: Exception) {
            _status.value = AgentStatus.ERROR
            throw AgentException.ProcessingError("Error processing input: ${e.message}", e)
        } finally {
            _status.value = AgentStatus.READY
        }
    }
    
    override suspend fun shutdown() {
        try {
            _status.value = AgentStatus.SHUTTING_DOWN
            
            // Shutdown sub-agents
            runCatching { auraAgent.shutdown() }
            runCatching { kaiAgent.shutdown() }
            
            isInitialized = false
            _status.value = AgentStatus.TERMINATED
        } catch (e: Exception) {
            _status.value = AgentStatus.ERROR
            throw AgentException.ShutdownError("Error during shutdown: ${e.message}", e)
        }
    }
    
    /**
     * Process a user message through the appropriate agent based on context
     * @param message The user's message
     * @param context Additional context for processing
     * @return The agent's response
     */
    suspend fun processMessage(message: String, context: Map<String, Any> = emptyMap()): String {
        // Add context to the input for the agent
        val processedInput = if (context.isNotEmpty()) {
            "$message\n\nContext: ${context.entries.joinToString { "${it.key}=${it.value}" }}"
        } else {
            message
        }
        
        return process(processedInput)
    }
}
