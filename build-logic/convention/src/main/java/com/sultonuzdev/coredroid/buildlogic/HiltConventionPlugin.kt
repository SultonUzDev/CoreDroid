package com.sultonuzdev.coredroid.buildlogic

import com.sultonuzdev.coredroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            dependencies {
                add("implementation", libs.findBundle("hilt").get())
            }
        }


    }
}