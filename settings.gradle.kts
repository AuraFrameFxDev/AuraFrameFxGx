pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.namespace) {
                "com.android" -> useModule("com.android.tools.build:gradle:${requested.version}")
                "org.jetbrains.kotlin" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "com.google.dagger.hilt.android" -> useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
                "com.google.gms.google-services" -> useModule("com.google.gms:google-services:${requested.version}")
                "com.google.firebase.crashlytics" -> useModule("com.google.firebase:firebase-crashlytics-gradle:${requested.version}")
                "com.google.firebase.firebase-perf" -> useModule("com.google.firebase:perf-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

// Version catalog is defined in gradle/libs.versions.toml
rootProject.name = "auraframefx"
include(":app")