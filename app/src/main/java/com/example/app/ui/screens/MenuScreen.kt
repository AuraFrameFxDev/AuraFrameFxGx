package com.example.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MenuScreen() { // Renamed to menuScreen
    // TODO: Implement the actual Menu Screen UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Menu Screen (Placeholder)")
    }
}

// @Preview(showBackground = true)
// @Composable
// fun MenuScreenPreview() { // Renamed
//     MenuScreen()
// }
