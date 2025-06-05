package com.example.app.ui.screens

// import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.app.system.homescreen.HomeScreenTransitionType
import com.example.app.ui.components.HologramTransition

@Composable
fun EcosystemMenuScreen(
    transitionType: HomeScreenTransitionType = HomeScreenTransitionType.DIGITAL_DECONSTRUCT,
    showHologram: Boolean = true,
) {
    HologramTransition(visible = showHologram) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Ecosystem Menu Screen (Placeholder)")
                DigitalTransitionRow(
                    currentType = transitionType,
                    onTypeSelected = {}
                )
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun EcosystemMenuScreenPreview() { // Renamed
//     EcosystemMenuScreen()
// }
