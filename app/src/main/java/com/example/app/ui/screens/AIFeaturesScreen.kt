package com.example.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// import androidx.compose.ui.tooling.preview.Preview // Uncomment for preview

@Composable
fun StatusCard(status: String, modifier: Modifier = Modifier) { // Renamed
    Card(modifier = modifier.padding(8.dp)) {
        Text(text = "Status: $status", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun FeatureCard(featureName: String, description: String, modifier: Modifier = Modifier) { // Renamed
    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = featureName)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description)
        }
    }
}

@Composable
fun AiFeaturesScreen() { // Renamed from AIFeaturesScreen
    // TODO: Implement the actual AI Features Screen UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "AI Features Screen (Placeholder)")
        Spacer(modifier = Modifier.height(16.dp))
        StatusCard(status = "All systems nominal.")
        Spacer(modifier = Modifier.height(8.dp))
        FeatureCard(featureName = "Smart Replies", description = "Contextual reply suggestions.")
    }
}

// @Preview(showBackground = true) // Uncomment for preview
// @Composable
// fun AiFeaturesScreenPreview() { // Renamed
//     AiFeaturesScreen()
// }
