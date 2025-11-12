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
    implementation(project(":feature:overview"))
    implementation(project(":feature:battery"))
    implementation(project(":feature:network"))
    implementation(project(":feature:sensors"))
    implementation(project(":feature:tools"))
    implementation(project(":feature:settings"))
    implementation(project(":core:db"))

    // Core modules
    implementation(project(":core:firebase"))
}