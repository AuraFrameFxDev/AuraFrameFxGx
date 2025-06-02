package com.example.app.ai.config

/**
 * Data class for holding AI configuration parameters.
 * TODO: Define actual properties based on application needs.
 * This is a general placeholder. Specific configurations (like VertexAIConfig) might also exist.
 */
data class AIConfig(
    val apiKey: String? = null, // Example property
    val modelName: String = "default-model", // Example property
    val useSecureStorage: Boolean = false, // Example property
    val temperature: Float = 0.7f, // Example property
    val maxOutputTokens: Int = 256 // Example property
    // Add other common AI configuration parameters as needed
) {
    // TODO: Reported as unused declaration. Ensure this config is used by AI components.
}
