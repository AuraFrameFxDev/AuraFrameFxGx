// Minimal settings.gradle.kts to debug version catalog issue
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// Comment out the version catalog temporarily
// versionCatalogs {
//     create("libs") {
//         from(files("gradle/libs.versions.toml"))
//     }
// }

rootProject.name = "auraframefx"
include(":app")