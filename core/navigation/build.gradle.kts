plugins {
    id("coredroid.android.library")
    id("coredroid.android.compose")
    alias(libs.plugins.kotlin.serialization)

}

dependencies {
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":feature:overview"))
    implementation(project(":feature:system"))
    implementation(project(":feature:battery"))
    implementation(project(":feature:network"))
    implementation(project(":feature:tools"))
    implementation(project(":feature:settings"))
}