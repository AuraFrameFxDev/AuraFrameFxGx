plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.kapt) // For Hilt WorkManager extensions if needed, or for other kapt-only processors
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization) // If using kotlinx-serialization
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka) // Add Dokka plugin
    // alias(libs.plugins.navigation.safe.args) // Only if using safe args for navigation
    // Apply KSP plugin
    id("com.google.devtools.ksp")
}

// Configure Dokka
tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka"))
    
    dokkaSourceSets {
        named("main") {
            moduleName.set("AuraFrameFX")
            moduleVersion.set(project.version.toString())
            
            // Include all source sets
            sourceRoots.from(file("src/main/kotlin"))
            
            // Android documentation
            noAndroidSdkLink.set(false)
            
            // External documentation
            externalDocumentationLink {
                url.set(uri("https://developer.android.com/reference/").toURL())
                packageListUrl.set(uri("https://developer.android.com/reference/package-list").toURL())
            }
        }
    }
}

// Repositories are managed in settings.gradle.kts

android {
    namespace = "com.example.app" // Ensure this matches your actual desired namespace
    compileSdk = libs.versions.compileSdk.get().toInt()
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
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
    // Compose BOM (Bill of Materials)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    debugImplementation(libs.compose.ui.tooling)
    
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
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
