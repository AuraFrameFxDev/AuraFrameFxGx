package com.example.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.tooling.preview.Preview // Uncomment for preview

@Composable
fun AiChatScreen() { // Renamed from AIChatScreen to aiChatScreen (lowercase first letter)
    // TODO: Implement the actual AI Chat Screen UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "AI Chat Screen (Placeholder)")
    }
}

// @Preview(showBackground = true) // Uncomment for preview
// @Composable
// fun AiChatScreenPreview() { // Renamed from AIChatScreenPreview
//     AiChatScreen()
// }
