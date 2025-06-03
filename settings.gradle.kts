pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        // maven { url = uri("https://maven.google.com") } // Redundant
        // maven { url = uri("https://dl.google.com/dl/android/maven2") } // Redundant
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        // maven { url = uri("https://repo1.maven.org/maven2/") } // Redundant
    }

    resolutionStrategy {
        eachPlugin {
            val pluginId = requested.id.id
            when (pluginId) {
                "com.android.application" -> useVersion("8.10.1")
                "org.jetbrains.kotlin.android",
                "org.jetbrains.kotlin.kapt",
                "org.jetbrains.kotlin.plugin.serialization",
                "org.jetbrains.kotlin.plugin.parcelize",
                "org.jetbrains.kotlin.plugin.compose" -> useVersion("2.1.21") // Updated Kotlin version
                "com.google.devtools.ksp" -> useVersion("2.1.21-2.0.1") // Updated KSP version
                "com.google.dagger.hilt.android" -> useVersion("2.50")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Recommended
    versionCatalogs {
        create("libs") {
            // Matching original syntax just in case
            (files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "auraframefx"
include(":app")