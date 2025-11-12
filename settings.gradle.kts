pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CoreDroid Advanced"

// App module
include(":app")

// Core modules
include(":core:common")
include(":core:ui")
include(":core:db")
include(":core:firebase")

// Feature modules
include(":feature:overview")
include(":feature:battery")
include(":feature:network")
include(":feature:sensors")
include(":feature:tools")
include(":feature:settings")

// Data modules
