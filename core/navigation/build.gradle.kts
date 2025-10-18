// ✅ OPTIMIZED with Convention Plugins

plugins {
    id("coredroid.android.library")
    id("coredroid.android.compose")
}


dependencies {
    implementation(project(":core:model"))
    implementation(libs.navigation.compose)
}

