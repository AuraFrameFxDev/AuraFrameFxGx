package com.example.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.app.system.overlay.*
import com.example.app.ui.theme.Color
import com.example.app.ui.theme.Shape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageShapeManagerScreen(
    viewModel: ImageShapeManagerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val images by viewModel.availableImages.collectAsState()
    val customImages by viewModel.customImages.collectAsState()
    val shapes by viewModel.shapes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image & Shape Manager") },
                actions = {
                    IconButton(onClick = { viewModel.openAddImageDialog() }) {
                        Icon(Icons.Default.Add, "Add Image")
                    }
                    IconButton(onClick = { viewModel.openAddShapeDialog() }) {
                        Icon(Icons.Default.Shape, "Add Shape")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openAddImageDialog() }
            ) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Image Grid
            Text(
                text = "Available Images",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(images) { image ->
                    ImageCard(
                        image = image,
                        isSelected = viewModel.selectedImage.value?.id == image.id,
                        onClick = { viewModel.selectImage(image) },
                        onEdit = { viewModel.openEditImageDialog(image) },
                        onDelete = { viewModel.deleteImage(image) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Custom Image Grid
            Text(
                text = "Custom Images",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(customImages) { image ->
                    ImageCard(
                        image = image,
                        isSelected = viewModel.selectedImage.value?.id == image.id,
                        onClick = { viewModel.selectImage(image) },
                        onEdit = { viewModel.openEditImageDialog(image) },
                        onDelete = { viewModel.deleteImage(image) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Shape Grid
            Text(
                text = "Available Shapes",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(shapes) { shape ->
                    ShapeCard(
                        shape = shape,
                        isSelected = viewModel.selectedShape.value?.id == shape.id,
                        onClick = { viewModel.selectShape(shape) },
                        onEdit = { viewModel.openEditShapeDialog(shape) },
                        onDelete = { viewModel.deleteShape(shape) }
                    )
                }
            }
        }
    }
}

@Composable
fun ImageCard(
    image: ImageResource,
    isSelected: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(Shape.RoundedRectangle),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF00FFCC).copy(alpha = 0.1f) else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = image.bitmap,
                contentDescription = image.id,
                modifier = Modifier
                    .size(64.dp)
                    .clip(Shape.RoundedRectangle),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = image.id,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = image.type.name,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete")
                }
            }
        }
    }
}

@Composable
fun ShapeCard(
    shape: OverlayShape,
    isSelected: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(Shape.RoundedRectangle),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF00FFCC).copy(alpha = 0.1f) else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(Shape.RoundedRectangle)
                    .background(Color(0xFF00FFCC))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = shape.id,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = shape.type.name,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddImageDialog(
    onDismiss: () -> Unit,
    onConfirm: (Bitmap) -> Unit,
) {
    var imageUri by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Custom Image") },
        text = {
            Column {
                OutlinedTextField(
                    value = imageUri ?: "",
                    onValueChange = { imageUri = it },
                    label = { Text("Image URI") },
                    placeholder = { Text("Enter image URI or path") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO: Implement image picker */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pick Image")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // TODO: Load image from URI and call onConfirm
                    onDismiss()
                },
                enabled = imageUri != null
            ) {
                Text("Add Image")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShapeDialog(
    onDismiss: () -> Unit,
    onConfirm: (OverlayShape) -> Unit,
) {
    var shapeType by remember { mutableStateOf(ShapeType.ROUNDED_RECTANGLE) }
    var properties by remember { mutableStateOf(mapOf<String, Any>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Custom Shape") },
        text = {
            Column {
                Text("Shape Type:")
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { /* no-op */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShapeType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = { shapeType = type },
                            selected = shapeType == type
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                // TODO: Add property editors based on shape type
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val shape = viewModel.shapeManager.createCustomShape(
                        type = shapeType,
                        properties = properties
                    )
                    onConfirm(shape)
                    onDismiss()
                }
            ) {
                Text("Add Shape")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
