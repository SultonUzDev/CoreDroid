plugins {
    id("coredroid.android.library")

}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:db"))
    implementation(project(":core:domain"))
}