package com.example.app.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AuraSparkleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Sparkle",
) { // Renamed to auraSparkleButton
    // TODO: Implement the actual Aura Sparkle Button with custom animation/effects
    Button(onClick = onClick, modifier = modifier) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun AuraSparkleButtonPreview() { // Renamed
    AuraSparkleButton(onClick = {})
}
