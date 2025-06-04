import java.io.File

fun main() {
    val sourceDir = File("app/src/main/java/com/example/app")
    val targetDir = File("app/src/main/java/com/genesis/ai/app")
    
    if (!sourceDir.exists()) {
        println("Source directory does not exist: ${sourceDir.absolutePath}")
        return
    }
    
    // Create target directory if it doesn't exist
    if (!targetDir.exists()) {
        targetDir.mkdirs()
        println("Created target directory: ${targetDir.absolutePath}")
    }
    
    // Copy and update files
    sourceDir.walk().forEach { sourceFile ->
        if (sourceFile.isFile) {
            val relativePath = sourceFile.relativeTo(sourceDir)
            val targetFile = File(targetDir, relativePath.path)
            
            // Create parent directories if they don't exist
            targetFile.parentFile?.mkdirs()
            
            if (sourceFile.extension in listOf("kt", "java", "xml")) {
                // For source files, update package and imports
                val content = sourceFile.readText()
                    .replace("package com.example.app", "package com.genesis.ai.app")
                    .replace("import com.example.app", "import com.genesis.ai.app")
                    .replace("com.example.app", "com.genesis.ai.app")
                
                targetFile.writeText(content)
                println("Updated: ${targetFile.absolutePath}")
            } else {
                // For other files, just copy
                sourceFile.copyTo(targetFile, overwrite = true)
                println("Copied: ${targetFile.absolutePath}")
            }
        }
    }
    
    // Update AndroidManifest.xml
    val manifestFile = File("app/src/main/AndroidManifest.xml")
    if (manifestFile.exists()) {
        val manifest = manifestFile.readText()
            .replace("package=\"com.example.app\"", "package=\"com.genesis.ai.app\"")
            .replace("android:name=\".", "android:name=\"com.genesis.ai.app.")
        
        manifestFile.writeText(manifest)
        println("Updated: ${manifestFile.absolutePath}")
    }
    
    // Update build.gradle.kts
    val buildGradle = File("app/build.gradle.kts")
    if (buildGradle.exists()) {
        val content = buildGradle.readText()
            .replace("namespace = \"com.example.app\"", "namespace = \"com.genesis.ai.app\"")
            .replace("applicationId = \"com.example.app\"", "applicationId = \"com.genesis.ai.app\"")
        
        buildGradle.writeText(content)
        println("Updated: ${buildGradle.absolutePath}")
    }
    
    println("\nMigration complete!")
    println("Please review the changes and test the application.")
    println("Once verified, you can safely delete the old package directory: ${sourceDir.absolutePath}")
}
