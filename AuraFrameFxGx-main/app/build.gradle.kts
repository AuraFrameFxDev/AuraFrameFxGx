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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "36.0.0"
    ndkVersion = "29.0.13113456 rc1"
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

    // AndroidX UI Components
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.core)
    implementation(libs.androidx.vectordrawable.animated)
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
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        // Use Kotlin's explicit API mode for better API visibility
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
}

// Custom task for OpenAPI code generation
tasks.register("generateApiClient") {
    group = "openapi tools"
    description = "Generate API client from OpenAPI spec"
    
    doLast {
        exec {
            workingDir = projectDir
            executable = "${rootProject.projectDir}/gradlew"
            args = listOf(
                "openApiGenerate",
                "--generator-name=kotlin",
                "--input-spec=${layout.projectDirectory}/src/main/resources/api-spec.yaml",
                "--output=${layout.buildDirectory}/generated/openapi",
                "--config-options=dateLibrary=java8,enumPropertyNaming=UPPERCASE,serializationLibrary=jackson,packageName=com.genesis.ai.api,useCoroutines=true,interfaceOnly=true,library=jvm-retrofit2",
                "--api-package=com.genesis.ai.api",
                "--invoker-package=com.genesis.ai.api.infrastructure",
                "--model-package=com.genesis.ai.api.models"
            )
        }
    }
}

// Run OpenAPI generation before build
tasks.named("preBuild") {
    dependsOn("generateApiClient")
}

// Make the OpenAPI generated code available to the main source set
kotlin {
    sourceSets {
        main {
            kotlin.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))

        }
    }
}
