package com.genesis.ai.app.ai.memory

import com.genesis.ai.app.model.AgentType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class MemoryItem(
    val id: String = "mem_${Clock.System.now().toEpochMilliseconds()}",
    val content: String,
    val timestamp: Instant = Clock.System.now(),
    val agent: AgentType,
    val context: String? = null,
    val priority: Float = 0.5f,
    val tags: List<String> = emptyList(),
    val metadata: Map<String, Any> = emptyMap()
)

@Serializable
data class MemoryQuery(
    val query: String,
    val context: String? = null,
    val maxResults: Int = 5,
    val minSimilarity: Float = 0.7f,
    val tags: List<String> = emptyList(),
    val timeRange: Pair<Instant, Instant>? = null,
    val agentFilter: List<AgentType> = emptyList()
)

@Serializable
data class MemoryRetrievalResult(
    val items: List<MemoryItem>,
    val total: Int,
    val query: MemoryQuery
)
