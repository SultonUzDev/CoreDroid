plugins {
    id("coredroid.android.application")
    id("coredroid.android.compose")
    id("coredroid.android.firebase")
    id("coredroid.android.koin")
}

dependencies {
    implementation(project(":feature:overview"))
    implementation(project(":feature:hardware"))
    implementation(project(":feature:battery"))
    implementation(project(":feature:monitor"))
    implementation(project(":feature:network"))
    implementation(project(":feature:sensors"))
    implementation(project(":feature:system"))
    implementation(project(":feature:tools"))
    implementation(project(":feature:apps"))
    implementation(project(":feature:settings"))

    // Core modules
    implementation(project(":core:navigation"))
    implementation(project(":core:preferences"))
    implementation(project(":core:firebase"))
}