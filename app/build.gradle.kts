plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // For Hilt WorkManager extensions if needed, or for other kapt-only processors
    alias(libs.plugins.ksp) // For Hilt core and other KSP processors
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization) // If using kotlinx-serialization
    // Add other plugins like google-services, firebase-crashlytics if they should be applied here
    // id("com.google.gms.google-services") // Example if not using alias from root for this one
}

android {
    namespace = "com.example.app" // Ensure this matches your actual desired namespace
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.app" // Ensure this matches
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
        sourceCompatibility = JavaVersion.VERSION_1_8 // Or VERSION_11 / VERSION_17 to match jvmTarget
        targetCompatibility = JavaVersion.VERSION_1_8 // Or VERSION_11 / VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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

    // Networking (OkHttp & Retrofit)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor) // Useful for debugging
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson) // Or your preferred converter

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
