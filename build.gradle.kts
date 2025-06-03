// AuraFrameFxBeta/build.gradle.kts

// This 'buildscript' block MUST appear before the root 'plugins { ... }' block.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        // These are the plugins/dependencies that Gradle needs to run *this build script*.
        // Their versions are hardcoded here because 'libs' is not yet available.
        // Ensure these match the versions defined in your libs.versions.toml.
        classpath("com.android.tools.build:gradle:8.10.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21") // New Kotlin version
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.1.21-2.0.1") // New KSP version
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:2.9.0")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
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
    // alias(libs.plugins.android.library) apply false // Commented out to resolve the error
    // id("org.jetbrains.kotlin.android") apply false // Removed this line
    // alias(libs.plugins.kotlin.kapt) apply false // Removed for direct application
    // alias(libs.plugins.kotlin.serialization) apply false // Removed for direct application

    // Code Quality Plugins (applied to all projects)
    // alias(libs.plugins.detekt) apply false // Temporarily removed
    // alias(libs.plugins.ktlint) apply false // Temporarily removed
    // alias(libs.plugins.spotless) apply false // Temporarily removed

    // Firebase plugins
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false

    // Dependency Injection
    alias(libs.plugins.hilt.android) apply false
    // alias(libs.plugins.ksp) apply false // Removed for direct application

    // Navigation
    alias(libs.plugins.navigation.safe.args) apply false

    // Documentation
    alias(libs.plugins.dokka)
    // alias(libs.plugins.kotlin.compose) apply false // Removed for direct application
}

// Common configurations for all projects
allprojects {
    // ... (rest of your existing allprojects block, it should be fine as is)
}

// ... (rest of your build.gradle.kts file)}