// Settings configured using Kotlin DSL
pluginManagement {
    // Define the repositories for Gradle plugins
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    // Configure plugin resolution strategy
    resolutionStrategy {
        eachPlugin {
            when (requested.id.namespace) {
                "com.android" -> useModule("com.android.tools.build:gradle:${requested.version}")
                "org.jetbrains.kotlin" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "com.google.dagger.hilt.android" -> useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
                "com.google.gms.google-services" -> useModule("com.google.gms:google-services:${requested.version}")
                "com.google.firebase.crashlytics" -> useModule("com.google.firebase:firebase-crashlytics-gradle:${requested.version}")
                "com.google.firebase.firebase-perf" -> useModule("com.google.firebase:perf-plugin:${requested.version}")
                "androidx.navigation.safeargs.kotlin" -> useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
            }
        }
    }
}

// Enable feature previews
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")


// Configure dependency resolution
dependencyResolutionManagement {
    // Use the same repositories for all projects
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    
    // Define repositories for dependencies
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
    
    // Enable version catalogs
    versionCatalogs {
        // Define the 'libs' version catalog from the TOML file
        create("libs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

// Root project configuration
rootProject.name = "auraframefx"

// Include the app module
include(":app")