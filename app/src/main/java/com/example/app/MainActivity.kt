package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme // Import MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
// Assuming Theme.App is a Composable Theme, potentially defined in a Theme.kt file
// For now, we'll call it directly. If it's XML only, this would need a Composable wrapper.
// import com.example.app.ui.theme.Theme.App // Example import if Theme.App is a Composable

class MainActivity : ComponentActivity() { // Changed parent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Removed TODO about unused entry point, as it's now used for Compose content.
        setContent {
            // Using MaterialTheme directly for now for robustness.
            // A custom Composable Theme.App wrapper would typically be defined in a ui.theme package
            // and would use the colors from colors.xml (converted to Compose Colors)
            // and typography from a Typography.kt file.
            MaterialTheme {
                Greeting("Welcome to AuraFrameFX!")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO: Reported as unused entry point by static analysis, but it's a valid lifecycle method.
        // TODO: Perform any cleanup here if needed.
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Similar to above, use MaterialTheme for preview robustness
    MaterialTheme {
        Greeting("AuraFrameFX Preview")
    }
}
