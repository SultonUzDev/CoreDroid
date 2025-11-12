plugins {
    id("coredroid.android.application")
    id("coredroid.android.compose")
    id("coredroid.android.firebase")
    id("coredroid.android.hilt")
}

android {
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    // Core modules
    implementation(project(":core:firebase"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(libs.navigation.compose)
}