plugins {
    id("coredroid.android.feature")
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))

    implementation(project(":core:ui"))
}

