// AuraFrameFxBeta/build.gradle.kts

// This 'buildscript' block MUST appear before the root 'plugins { ... }' block.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo1.maven.org/maven2/") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.openapitools.org/repository/releases/") }
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
        classpath("org.openapitools:openapi-generator-gradle-plugin:5.3.0")
    }
}

// This 'plugins' block applies plugins to your *project*
plugins {
    kotlin("android") version "2.1.21" apply false
    kotlin("kapt") version "2.1.21" apply false
    id("com.android.application") version "8.10.1" apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.21" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.1.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21" apply false

    // Firebase plugins
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false

    // Dependency Injection
    id("com.google.dagger.hilt.android") version "2.50" apply false

    // Navigation
    id("androidx.navigation.safeargs.kotlin") version "2.9.0" apply false

    // Documentation
    id("org.jetbrains.dokka") version "1.9.20" apply false

    // Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}

// Common configurations for all projects
allprojects {
    // Repositories are now defined in settings.gradle.kts
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

// Apply Hilt and KSP to all modules that need it
subprojects {
    plugins.withId("com.android.application") {
        apply(plugin = "com.google.dagger.hilt.android")
        apply(plugin = "com.google.devtools.ksp")
    }

    // Apply kapt for annotation processing
    plugins.withId("org.jetbrains.kotlin.android") {
        apply(plugin = "org.jetbrains.kotlin.kapt")
    }
}