package com.sultonuzdev.coredroid.buildlogic

import com.android.build.api.dsl.LibraryExtension
import com.sultonuzdev.coredroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply("coredroid.android.library")
                apply("coredroid.android.compose")
                apply("coredroid.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                testOptions.animationsDisabled = true

            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:ui"))

                add("implementation", libs.findLibrary("navigation.compose").get())
                add("implementation", libs.findBundle("coroutines").get())


            }
        }
    }


}