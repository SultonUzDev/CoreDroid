plugins {
    `kotlin-dsl`
}
group = "com.sultonuzdev.coredroid.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "coredroid.android.application"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "coredroid.android.compose"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "coredroid.android.library"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.AndroidLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "coredroid.android.feature"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.AndroidFeatureConventionPlugin"
        }
        register("androidFirebase") {
            id = "coredroid.android.firebase"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.AndroidFirebaseConventionPlugin"
        }

        register("androidTest") {
            id = "coredroid.android.test"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.AndroidTestConventionPlugin"
        }
        register("hilt") {
            id = "coredroid.android.hilt"
            implementationClass = "com.sultonuzdev.coredroid.buildlogic.HiltConventionPlugin"
        }


    }
}