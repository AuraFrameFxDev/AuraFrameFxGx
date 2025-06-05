plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.openapi.generator)
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    
    // Legacy Support & UI
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.support.v4)
    implementation(libs.androidx.support.core.ui)
    implementation(libs.androidx.recyclerview.v7)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.drawerlayout) 
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.animated.vector.drawable)
    implementation(libs.androidx.exifinterface)
    
    // Room Database
    implementation(libs.androidx.room.ktx)
    
    // ViewBinding & DataBinding
    implementation(libs.androidx.viewbinding)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.databinding.adapters)
    implementation(libs.androidx.databinding.library)
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    
    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.jetbrains.compose.resources.android)

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)

    // Kotlin Core Libraries
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.script.runtime)
    implementation(libs.kotlin.script.util)
    implementation(libs.kotlin.scripting.jvm)
    implementation(libs.kotlin.scripting.compiler)
    implementation(libs.kotlin.scripting.dependencies)
    implementation(libs.kotlin.bom)
    
    // KotlinX
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.messaging.ktx)
    
    // Google AI & Material
    implementation(libs.google.ai.generativeai)
    implementation(libs.google.android.material)

    // Networking
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.volley)
    implementation(libs.grpc.kotlin.stub)
    
    // UI Components & Utils
    implementation(libs.glide)
    implementation(libs.easy.permissions)
    implementation(libs.agentweb)
    
    // JSON & Data Handling
    implementation(libs.jackson.module.kotlin)
    
    // Utilities
    implementation(libs.timber)
    implementation(libs.vertx.kotlin.coroutines)
    implementation(libs.util.kotlin)
    implementation(libs.gsdk.common)
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.android)
}

// Kotlin compiler configuration
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
        // Use Kotlin's explicit API mode for better API visibility
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
}

// OpenAPI Generator configuration
openApiGenerate {
    generatorName.set("kotlin")
    // Define the source OpenAPI specification file
    inputSpec.set("${project.projectDir}/src/main/resources/api-spec.yaml") 
    // Output directory for generated code
    outputDir.set("${project.buildDir}/generated/openapi")
    
    // Configure the Kotlin generator
    configOptions.set(mapOf(
        "dateLibrary" to "java8",
        "enumPropertyNaming" to "UPPERCASE",
        "serializationLibrary" to "jackson", // Use Jackson for serialization
        "packageName" to "com.genesis.ai.api",
        "useCoroutines" to "true", // Enable Kotlin coroutines
        "interfaceOnly" to "true", // Generate only interfaces for API clients
        "library" to "jvm-retrofit2" // Use Retrofit2 for API client generation
    ))
    
    // Configure output settings
    apiPackage.set("com.genesis.ai.api")
    invokerPackage.set("com.genesis.ai.api.infrastructure")
    modelPackage.set("com.genesis.ai.api.models")
}

// Make the OpenAPI generated code available to the main source set
kotlin {
    sourceSets {
        main {
            kotlin.srcDir("${project.buildDir}/generated/openapi/src/main/kotlin")
        }
    }
}
