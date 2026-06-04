

plugins {
    id("coredroid.android.library")

}


dependencies {

    implementation(project(":core:common"))
    implementation(libs.firebase.crashlytics)

}

