package com.sultonuzdev.coredroid

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType


val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

object ProjectConfig {
    const val compileSdk = 35
    const val minSdk = 27
    const val targetSdk = 34

    const val applicationId = "com.sultonuzdev.coredroid"
    const val namespace = "com.sultonuzdev.coredroid"

    object Versions {
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

}