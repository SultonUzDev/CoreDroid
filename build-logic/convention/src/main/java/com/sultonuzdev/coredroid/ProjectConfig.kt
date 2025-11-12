package com.sultonuzdev.coredroid

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType


val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

object ProjectConfig {
    const val compileSdk = 36
    const val minSdk = 27
    const val targetSdk = 36

    const val applicationId = "com.sultonuzdev.coredroid"
    const val namespace = "com.sultonuzdev.coredroid"

    object Versions {
        const val versionCode = 2
        const val versionName = "1.0.2"
    }

}