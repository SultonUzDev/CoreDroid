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
include(":core:designsystem")
include(":core:model")
include(":core:data")
include(":core:navigation")
include(":core:preferences")
include(":core:firebase")
include(":core:testing")

// Feature modules
include(":feature:overview")
include(":feature:hardware")
include(":feature:battery")
include(":feature:monitor")
include(":feature:network")
include(":feature:sensors")
include(":feature:system")
include(":feature:tools")
include(":feature:apps")
include(":feature:settings")

// Data modules
include(":data:device")
include(":data:battery")
include(":data:sensors")
include(":data:network")
include(":data:system")