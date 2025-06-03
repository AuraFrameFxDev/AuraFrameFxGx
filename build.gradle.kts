// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        // These are the plugins/dependencies that Gradle needs to run *this build script*.
        // Note: These versions should match those in libs.versions.toml
        val agpVersion = libs.versions.agp.get()
        val kotlinVersion = libs.versions.kotlin.get()
        val hiltVersion = libs.versions.hilt.get()
        val navVersion = libs.versions.androidxNavigation.get()
        
        classpath("com.android.tools.build:gradle:$agpVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:${libs.versions.googleServicesPlugin.get()}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${libs.versions.firebaseCrashlyticsPlugin.get()}")
        classpath("com.google.firebase:perf-plugin:${libs.versions.firebasePerfPlugin.get()}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:$navVersion")
    }
}

// Top-level plugins block
plugins {
    // Core plugins
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.compose) apply false
    
    // Hilt
    alias(libs.plugins.hilt.android) apply false
    
    // Firebase
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    
    // Navigation
    alias(libs.plugins.navigation.safe.args) apply false
    
    // Documentation
    alias(libs.plugins.dokka) apply false
    
    // KSP
    alias(libs.plugins.ksp) apply false
}

// Common configurations for all projects
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = libs.versions.jvmTarget.get()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
            )
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}