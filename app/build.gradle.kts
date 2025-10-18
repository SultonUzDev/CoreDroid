plugins {
    id("coredroid.android.application")
    id("coredroid.android.compose")
    id("coredroid.android.firebase")
}

android {
    namespace = "com.sultonuzdev.coredroid"

//    compileSdk = libs.versions.compileSdk.get().toInt()
//
//    defaultConfig {
//        applicationId = "com.sultonuzdev.coredroid"
//        minSdk = libs.versions.minSdk.get().toInt()
//        targetSdk = libs.versions.targetSdk.get().toInt()
//        versionCode = 1
//        versionName = "1.0.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }

//    buildTypes {
//        release {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            signingConfig = signingConfigs.getByName("debug")
//        }
//        debug {
//            isDebuggable = true
//            applicationIdSuffix = ".debug"
//            versionNameSuffix = "-debug"
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//
//
//    kotlinOptions {
//        jvmTarget = "17"
//        freeCompilerArgs += listOf(
//            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
//            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
//            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
//        )
//    }
//
//    buildFeatures {
//        compose = true
//        buildConfig = true
//    }
//
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
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

    // Koin
    implementation(libs.bundles.koin)
}