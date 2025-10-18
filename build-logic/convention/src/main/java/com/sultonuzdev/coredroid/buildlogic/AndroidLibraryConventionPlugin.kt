package com.sultonuzdev.coredroid.buildlogic

import com.android.build.api.dsl.LibraryExtension
import com.sultonuzdev.coredroid.ProjectConfig
import com.sultonuzdev.coredroid.configureKotlinAndroid
import com.sultonuzdev.coredroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                // Auto-calculate namespace from project path
                // e.g., :feature:battery -> com.sultonuzdev.coredroid.feature.battery
                namespace = "${ProjectConfig.namespace}${path.replace(":", ".").replace("-", ".")}"
                configureKotlinAndroid(this)
                lint.targetSdk = ProjectConfig.targetSdk

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
                buildFeatures {
                    buildConfig = true
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("timber").get())

                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))


            }


        }
    }

}