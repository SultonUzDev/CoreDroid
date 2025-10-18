package com.sultonuzdev.coredroid.buildlogic

import com.android.build.gradle.TestExtension
import com.sultonuzdev.coredroid.ProjectConfig
import com.sultonuzdev.coredroid.configureKotlinAndroid
import com.sultonuzdev.coredroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension>() {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ProjectConfig.targetSdk
            }
            dependencies {
                add("implementation", project(":core:testing"))
                add("implementation", libs.findBundle("testing").get())
                add("implementation", libs.findBundle("android.testing").get())
            }
        }
    }
}