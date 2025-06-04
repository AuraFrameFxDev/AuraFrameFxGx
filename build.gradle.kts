// AuraFrameFxBeta/build.gradle.kts

// This 'buildscript' block MUST appear before the root 'plugins { ... }' block.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://dl.google.com/dl/android/maven2") }
    }
    dependencies {
        // These are the plugins/dependencies that Gradle needs to run *this build script*.
        // Using hardcoded versions here since 'libs' is not available in buildscript block
        classpath("com.android.tools.build:gradle:8.1.0")  // AGP 8.1.0 for Gradle 8.5
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")  // Kotlin 2.1.21
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")  // Hardcoded version matching libs.versions.toml
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:2.7.7")  // Hardcoded version
        // KSP plugin is managed through version catalog
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.1.21-2.0.1")
    }
}
// This section MUST be at the very top of your build.gradle.kts file.
// --- START OF CRITICAL CORRECTION --- has been moved to the top
// --- END OF CRITICAL CORRECTION ---

// Top-level val definitions (accessible everywhere AFTER they are defined, but not in buildscript)
// Removed unused val declarations

// This 'plugins' block applies plugins to your *project*. It comes AFTER 'buildscript'.
plugins {
    // Core plugins
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    
    // KSP - Using direct ID since it's not in version catalog by default
    id("com.google.devtools.ksp") version "${libs.versions.ksp.get()}" apply false
    
    // Compose
    id("org.jetbrains.compose") version "1.5.10" apply false
    
    // Hilt
    alias(libs.plugins.hilt.android) apply false
    
    // Firebase
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false

    // Dependency Injection
    alias(libs.plugins.hilt.android) apply false
    // KSP is applied in the app/build.gradle.kts file

    // Navigation
    alias(libs.plugins.navigation.safe.args) apply false

    // Documentation
    alias(libs.plugins.dokka) apply false


// Common configurations for all projects
allprojects {
    // ... (rest of your existing allprojects block, it should be fine as is)
}}

// ... (rest of your build.gradle.kts file)}