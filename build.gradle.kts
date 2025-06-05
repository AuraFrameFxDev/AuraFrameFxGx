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
        classpath("com.android.tools.build:gradle:8.10.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.1.21-2.0.1")
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:2.9.0")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
        // Add OpenAPI Generator
        classpath("org.openapitools:openapi-generator-gradle-plugin:4.3.1")
    }
}

// This 'plugins' block applies plugins to your *project*
plugins {
    // Core plugins - using apply false to avoid applying to root project
    alias(libs.plugins.android.application) apply false
    kotlin("android") version "2.1.21" apply false
    kotlin("kapt") version "2.1.21" apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.21" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.1.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21" apply false
    
    // Firebase plugins
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false

    // Dependency Injection
    alias(libs.plugins.hilt.android) apply false

    // Navigation
    alias(libs.plugins.navigation.safe.args) apply false

    // Documentation
    alias(libs.plugins.dokka)
    
    // OpenAPI Generator
    id("org.openapitools.generator") version "4.3.1" apply false
}

// Common configurations for all projects
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}