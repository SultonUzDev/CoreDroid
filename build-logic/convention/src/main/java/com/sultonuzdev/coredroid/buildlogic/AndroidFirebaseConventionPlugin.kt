package com.sultonuzdev.coredroid.buildlogic

import com.sultonuzdev.coredroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")

            }

            dependencies {
                add("implementation", libs.findLibrary("firebase.crashlytics").get())
            }
        }


    }
}