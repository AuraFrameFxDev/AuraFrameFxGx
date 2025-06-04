package com.genesis.ai.app.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.genesis.ai.app.ui.theme.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

/**
 * Represents a message in the chat
 * @property id Unique identifier for the message
 * @property content The text content of the message
 * @property timestamp When the message was sent
 * @property sender The sender of the message
 * @property isFromUser Whether the message is from the user (true) or an AI agent (false)
 * @property isTyping Whether the message is a typing indicator (for AI responses)
 * @property agentType The type of agent that sent the message (for AI messages)
 */
@Immutable
data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val sender: String = "",
    val isFromUser: Boolean = false,
    val isTyping: Boolean = false,
    val agentType: AgentType = AgentType.GENESIS
) {
    /**
     * Format the timestamp as a user-friendly string
     */
    fun formattedTime(): String {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        return timestamp.format(formatter)
    }
    
    companion object {
        /**
         * Create a user message
         */
        fun user(content: String): Message {
            return Message(
                content = content,
                isFromUser = true,
                sender = "You"
            )
        }
        
        /**
         * Create an AI agent message
         */
        fun agent(
            content: String,
            agentType: AgentType,
            isTyping: Boolean = false
        ): Message {
            return Message(
                content = content,
                isFromUser = false,
                sender = agentType.displayName,
                isTyping = isTyping,
                agentType = agentType
            )
        }
        
        /**
         * Create a typing indicator message for an AI agent
         */
        fun typing(agentType: AgentType): Message {
            return agent(
                content = "",
                agentType = agentType,
                isTyping = true
            )
        }
    }
}

/**
 * Represents the type of AI agent
 */
enum class AgentType(
    val id: String,
    val displayName: String,
    val color: Color
) {
    GENESIS("genesis", "Genesis", Primary),
    AURA("aura", "Aura", Secondary),
    KAI("kai", "Kai", Tertiary);
    
    companion object {
        fun fromId(id: String): AgentType {
            return values().find { it.id == id } ?: GENESIS
        }
    }
}

/**
 * Sample messages for previews and testing
 */
object SampleMessages {
    val userMessage = Message.user("Hello, Aura! How are you today?")
    
    val auraResponse = Message.agent(
        content = "Hello! I'm doing great, thanks for asking! How can I assist you today?",
        agentType = AgentType.AURA
    )
    
    val genesisTyping = Message.typing(AgentType.GENESIS)
    
    val conversation = listOf(
        Message.agent(
            content = "Welcome to AuraFrameFX! I'm Genesis, your AI assistant.",
            agentType = AgentType.GENESIS
        ),
        userMessage,
        auraResponse,
        genesisTyping
    )
}
