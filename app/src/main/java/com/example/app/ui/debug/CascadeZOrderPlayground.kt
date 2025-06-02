package com.example.app.ui.debug

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.ai.agents.CascadeAgent
import com.example.app.model.agent_states.ProcessingState
import com.example.app.model.agent_states.VisionState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CascadeDebugViewModel @Inject constructor(
    private val cascadeAgent: CascadeAgent
) {
    val visionState: StateFlow<VisionState> = cascadeAgent.visionState
    val processingState: StateFlow<ProcessingState> = cascadeAgent.processingState

    fun updateVisionState(newState: VisionState) {
        cascadeAgent.updateVisionState(newState)
    }

    fun updateProcessingState(newState: ProcessingState) {
        cascadeAgent.updateProcessingState(newState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CascadeZOrderPlayground(
    viewModel: CascadeDebugViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    var newVisionState by remember { mutableStateOf(VisionState()) }
    var newProcessingState by remember { mutableStateOf(ProcessingState()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cascade State Debugger",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Vision State",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Current State: ${viewModel.visionState.value}",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = newVisionState.toString(),
                    onValueChange = { 
                        // Parse and update vision state
                    },
                    label = { Text("New Vision State") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.updateVisionState(newVisionState) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Vision State")
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Processing State",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Current State: ${viewModel.processingState.value}",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = newProcessingState.toString(),
                    onValueChange = { 
                        // Parse and update processing state
                    },
                    label = { Text("New Processing State") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.updateProcessingState(newProcessingState) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Processing State")
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "State History",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    item {
                        Text(
                            text = "Vision History",
                            style = MaterialTheme.typography.titleSmall
                        )
                        viewModel.visionState.value.history?.forEach { entry ->
                            Text(text = "- $entry")
                        }
                    }
                    item {
                        Text(
                            text = "Processing History",
                            style = MaterialTheme.typography.titleSmall
                        )
                        viewModel.processingState.value.history?.forEach { entry ->
                            Text(text = "- $entry")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun CascadeZOrderPlaygroundPreview() {
    MaterialTheme {
        CascadeZOrderPlayground()
    }
}
