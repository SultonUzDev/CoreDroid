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

rootProject.name = "CoreDroid"

// App module
include(":app")

// Core modules
include(":core:common")
include(":core:ui")
include(":core:db")
include(":core:firebase")
include(":core:navigation")
include(":core:data")
include(":core:domain")


// Feature modules
include(":feature:overview")
include(":feature:battery")
include(":feature:network")
include(":feature:system")
include(":feature:tools")
include(":feature:settings")


