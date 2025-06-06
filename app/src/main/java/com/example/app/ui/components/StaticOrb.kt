package com.genesis.ai.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StaticOrb(modifier: Modifier = Modifier, color: Color = Color.Gray, text: String = "Orb") { // Renamed to staticOrb
    // TODO: Implement the actual Static Orb UI, potentially with different states or appearances
    Box(
        modifier = modifier
            .size(80.dp) // Example size
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun StaticOrbPreview() { // Renamed
    StaticOrb(color = Color.Magenta, text = "Static")
}
