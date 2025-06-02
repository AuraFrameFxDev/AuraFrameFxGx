package com.example.app.ai.error

import com.example.app.model.AgentType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AIError(
    val id: String = "err_${Clock.System.now().toEpochMilliseconds()}",
    val timestamp: Instant = Clock.System.now(),
    val agent: AgentType,
    val type: ErrorType,
    val message: String,
    val context: String,
    val metadata: Map<String, Any> = emptyMap(),
    val recoveryAttempts: Int = 0,
    val recoveryStatus: RecoveryStatus = RecoveryStatus.PENDING,
    val recoveryActions: List<RecoveryAction> = emptyList()
)

@Serializable
data class RecoveryAction(
    val id: String = "act_${Clock.System.now().toEpochMilliseconds()}",
    val timestamp: Instant = Clock.System.now(),
    val actionType: RecoveryActionType,
    val description: String,
    val result: RecoveryResult? = null,
    val metadata: Map<String, Any> = emptyMap()
)

enum class ErrorType {
    PROCESSING_ERROR,
    MEMORY_ERROR,
    CONTEXT_ERROR,
    NETWORK_ERROR,
    TIMEOUT_ERROR,
    INTERNAL_ERROR,
    USER_ERROR
}

enum class RecoveryStatus {
    PENDING,
    IN_PROGRESS,
    SUCCESS,
    FAILURE,
    SKIPPED
}

enum class RecoveryActionType {
    RETRY,
    FALLBACK,
    RESTART,
    RECONFIGURE,
    NOTIFY,
    ESCALATE
}

enum class RecoveryResult {
    SUCCESS,
    FAILURE,
    PARTIAL_SUCCESS,
    SKIPPED,
    UNKNOWN
}
