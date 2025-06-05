plugins {
    alias(libs.plugins.android.application) // Keep using alias for AGP
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt.android) // Keep using alias for Hilt
    id("org.jetbrains.kotlin.plugin.serialization")
    alias(libs.plugins.google.services) // Keep using alias for Firebase
    alias(libs.plugins.firebase.crashlytics) // Keep using alias for Firebase
    alias(libs.plugins.firebase.perf) // Keep using alias for Firebase
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.parcelize")
    // alias(libs.plugins.navigation.safe.args) // Only if using safe args for navigation
}

android {
    namespace = "com.genesis.ai.app" // Ensure this matches your actual desired namespace
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.genesis.ai.app" // Ensure this matches
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Configure ProGuard/R8 for release builds
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat) // If using AppCompat themes/views
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // AndroidX Navigation
    implementation(libs.androidx.navigation.compose)
    // implementation(libs.androidx.navigation.fragment.ktx) // Uncomment if using Fragments
    // implementation(libs.androidx.navigation.ui.ktx)    // Uncomment if using Fragments

    // AndroidX WorkManager (Optional, based on usage)
    // implementation(libs.androidx.work.runtime.ktx)

    // Jetpack Compose (BOM already included in libs.versions.toml, so just declare modules)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // Use KSP for Hilt
    // implementation(libs.androidx.hilt.work) // For Hilt + WorkManager
    // kapt(libs.androidx.hilt.compiler)    // Kapt for Hilt WorkManager extensions

    // KotlinX Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // KotlinX Serialization (If used)
    implementation(libs.kotlinx.serialization.json)

    // Firebase (BOM included in libs.versions.toml)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.messaging.ktx)
    // Add other Firebase dependencies as needed (firestore, auth, etc.)

    // Google AI Generative AI
    implementation(libs.google.ai.generativeai)

    // Google Material Components
    implementation(libs.google.android.material)

    // Networking (OkHttp & Retrofit)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor) // Useful for debugging
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson) // Or other converters like kotlinx.serialization

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // For Compose tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // For Compose tests
    debugImplementation(libs.androidx.compose.ui.tooling) // For Compose Previews in debug builds
    // MockK for testing
    testImplementation(libs.mockk.agent)
    testImplementation(libs.mockk.android) // or testImplementation(libs.mockk.jvm) if preferred for unit tests
    androidTestImplementation(libs.mockk.android)


    // TODO: Add any other specific dependencies the project needs.
}

// Apply Hilt Kapt plugin if using kapt for Hilt WorkManager or other specific processors
// Ensure this is compatible with KSP usage for core Hilt.
// It's often better to standardize on KSP if possible.
// tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//    kotlinOptions {
//        // Required for Hilt KAPT mode if there are Java classes using Hilt.
//        // freeCompilerArgs = freeCompilerArgs + "-Xopt-in=dagger.hilt.android.internal.disableMissingCopyInternal"
//    }
// }
