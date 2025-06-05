pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://repo1.maven.org/maven2/") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.openapitools.org/repository/releases/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
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
                "org.jetbrains.kotlin.plugin.compose",
                    -> useVersion("2.1.21") // Updated Kotlin version
                "com.google.devtools.ksp" -> useVersion("2.1.21-2.0.1") // Updated KSP version
                "com.google.dagger.hilt.android" -> useVersion("2.50")
            }
        }
        eachPlugin {
            if (requested.id.id == "org.openapitools.generator") {
                useModule("org.openapitools:openapi-generator-gradle-plugin:5.3.0")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Recommended
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://repo1.maven.org/maven2/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://maven.openapitools.org/repository/releases/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
    versionCatalogs {
        create("libs")
    }
}

rootProject.name = "auraframefx"
include(":app")