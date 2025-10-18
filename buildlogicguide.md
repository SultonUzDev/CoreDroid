# Build Logic Convention Plugins Guide

This guide will walk you through creating a `build-logic` module with convention plugins for your CoreDroid multi-module Android project step by step.

## Table of Contents
1. [Overview](#overview)
2. [Why Convention Plugins?](#why-convention-plugins)
3. [Directory Structure](#directory-structure)
4. [Step-by-Step Implementation](#step-by-step-implementation)
5. [Plugin Implementations](#plugin-implementations)
6. [Helper Files](#helper-files)
7. [Integration](#integration)
8. [Usage Examples](#usage-examples)

---

## Overview

Convention plugins are a Gradle best practice for sharing build configuration across multiple modules in a multi-module project. Instead of duplicating build logic in each module's `build.gradle.kts`, you define reusable plugins that encapsulate common configurations.

## Why Convention Plugins?

**Benefits:**
- ✅ **DRY Principle**: Write configuration once, use it everywhere
- ✅ **Consistency**: All modules use the same configuration
- ✅ **Maintainability**: Update configuration in one place
- ✅ **Type Safety**: Kotlin DSL with full IDE support
- ✅ **Performance**: Gradle can cache and parallelize plugin applications

**Without Convention Plugins:**
```kotlin
// Repeated in every feature module
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    compileSdk = 34
    defaultConfig {
        minSdk = 27
        // ... same configuration repeated everywhere
    }
}

dependencies {
    // Same dependencies in every feature module
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    // ... etc
}
```

**With Convention Plugins:**
```kotlin
// Simple, clean, consistent
plugins {
    id("coredroid.android.feature")
}
```

---

## Directory Structure

Create this complete structure:

```
CoreDroid/
├── build-logic/
│   ├── convention/
│   │   ├── build.gradle.kts
│   │   └── src/main/kotlin/
│   │       ├── AndroidApplicationConventionPlugin.kt
│   │       ├── AndroidLibraryConventionPlugin.kt
│   │       ├── AndroidFeatureConventionPlugin.kt
│   │       ├── AndroidComposeConventionPlugin.kt
│   │       ├── AndroidFirebaseConventionPlugin.kt
│   │       ├── AndroidTestConventionPlugin.kt
│   │       ├── KoinConventionPlugin.kt
│   │       └── com/sultonuzdev/coredroid/
│   │           ├── ProjectConfig.kt
│   │           ├── KotlinAndroid.kt
│   │           └── AndroidCompose.kt
│   └── settings.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```

---

## Step-by-Step Implementation

### Step 1: Create Directory Structure

```bash
# From project root
mkdir -p build-logic/convention/src/main/kotlin/com/sultonuzdev/coredroid
```

### Step 2: Create build-logic/settings.gradle.kts

**File:** `build-logic/settings.gradle.kts`

```kotlin
@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
```

**Explanation:**
- `dependencyResolutionManagement`: Configures where to find dependencies
- `gradlePluginPortal()`: Needed for KSP and other Gradle plugins
- `versionCatalogs`: Shares the main project's version catalog with build-logic
- `include(":convention")`: Declares the convention module

### Step 3: Create build-logic/convention/build.gradle.kts

**File:** `build-logic/convention/build.gradle.kts`

```kotlin
plugins {
    `kotlin-dsl`
}

group = "com.sultonuzdev.coredroid.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "coredroid.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "coredroid.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "coredroid.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "coredroid.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidFirebase") {
            id = "coredroid.android.firebase"
            implementationClass = "AndroidFirebaseConventionPlugin"
        }
        register("androidTest") {
            id = "coredroid.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("koin") {
            id = "coredroid.koin"
            implementationClass = "KoinConventionPlugin"
        }
    }
}
```

**Explanation:**
- `kotlin-dsl`: Enables writing Gradle plugins in Kotlin
- `compileOnly`: Gradle plugins needed to compile our convention plugins
- `gradlePlugin`: Registers all our custom plugins with their IDs

### Step 4: Update gradle/libs.versions.toml

Make sure these entries exist in your version catalog:

```toml
[libraries]
# Add these for build-logic
android-gradlePlugin = { module = "com.android.tools.build:gradle", version.ref = "androidGradlePlugin" }
kotlin-gradlePlugin = { module = "org.jetbrains.kotlin:kotlin-gradle-plugin", version.ref = "kotlin" }
compose-gradlePlugin = { module = "org.jetbrains.kotlin:compose-compiler-gradle-plugin", version.ref = "kotlin" }
```

---

## Plugin Implementations

### Step 5: Create Helper Files

#### File 1: ProjectConfig.kt

**Location:** `build-logic/convention/src/main/kotlin/com/sultonuzdev/coredroid/ProjectConfig.kt`

```kotlin
package com.sultonuzdev.coredroid

object ProjectConfig {
    const val COMPILE_SDK = 34
    const val MIN_SDK = 27
    const val TARGET_SDK = 34

    const val NAMESPACE = "com.sultonuzdev.coredroid"

    const val JVM_TARGET = "17"
}
```

**Purpose:** Centralized project configuration constants.

#### File 2: KotlinAndroid.kt

**Location:** `build-logic/convention/src/main/kotlin/com/sultonuzdev/coredroid/KotlinAndroid.kt`

```kotlin
package com.sultonuzdev.coredroid

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = ProjectConfig.COMPILE_SDK

        defaultConfig {
            minSdk = ProjectConfig.MIN_SDK
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = ProjectConfig.JVM_TARGET
        }
    }
}
```

**Purpose:** Shared function to configure Kotlin and Android settings consistently.

#### File 3: AndroidCompose.kt

**Location:** `build-logic/convention/src/main/kotlin/com/sultonuzdev/coredroid/AndroidCompose.kt`

```kotlin
package com.sultonuzdev.coredroid

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.14"
        }
    }
}
```

**Purpose:** Shared function to configure Jetpack Compose settings.

---

### Step 6: Create Convention Plugins

#### Plugin 1: AndroidApplicationConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt`

```kotlin
import com.android.build.api.dsl.ApplicationExtension
import com.sultonuzdev.coredroid.ProjectConfig
import com.sultonuzdev.coredroid.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = ProjectConfig.TARGET_SDK

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}
```

**Purpose:** Configures Android application modules (like `:app`).

**What it does:**
- Applies Android Application plugin
- Applies Kotlin Android plugin
- Sets up Kotlin/Android configuration
- Configures vector drawables
- Configures resource packaging

#### Plugin 2: AndroidLibraryConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/AndroidLibraryConventionPlugin.kt`

```kotlin
import com.android.build.gradle.LibraryExtension
import com.sultonuzdev.coredroid.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
        }
    }
}
```

**Purpose:** Configures Android library modules (like `:core:*`, `:data:*`).

**What it does:**
- Applies Android Library plugin
- Applies Kotlin Android plugin
- Sets up Kotlin/Android configuration
- Configures test runner

#### Plugin 3: AndroidComposeConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/AndroidComposeConventionPlugin.kt`

```kotlin
import com.android.build.api.dsl.CommonExtension
import com.sultonuzdev.coredroid.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<CommonExtension<*, *, *, *, *, *>>()
            configureAndroidCompose(extension)

            dependencies {
                val bom = platform(libs.findLibrary("compose.bom").get())
                add("implementation", bom)
                add("androidTestImplementation", bom)

                add("implementation", libs.findLibrary("compose.ui").get())
                add("implementation", libs.findLibrary("compose.ui.graphics").get())
                add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("compose.material3").get())

                add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("compose.ui.test.manifest").get())
            }
        }
    }

    private val Project.libs
        get() = extensions.getByName("libs") as org.gradle.api.artifacts.VersionCatalog
}
```

**Purpose:** Configures Jetpack Compose for any module.

**What it does:**
- Applies Compose Compiler plugin
- Enables Compose features
- Adds Compose BOM and core dependencies
- Adds debug dependencies for tooling

#### Plugin 4: AndroidFeatureConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/AndroidFeatureConventionPlugin.kt`

```kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("coredroid.android.library")
                apply("coredroid.android.compose")
                apply("coredroid.koin")
            }

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:designsystem"))

                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())

                add("implementation", libs.findLibrary("navigation.compose").get())
            }
        }
    }

    private val Project.libs
        get() = extensions.getByName("libs") as org.gradle.api.artifacts.VersionCatalog
}
```

**Purpose:** Configures feature modules with all common dependencies.

**What it does:**
- Applies library, compose, and koin conventions
- Links to core UI modules
- Adds lifecycle and navigation dependencies
- Sets up everything a feature module needs

#### Plugin 5: KoinConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/KoinConventionPlugin.kt`

```kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("koin.android").get())
                add("implementation", libs.findLibrary("koin.androidx.compose").get())
                add("implementation", libs.findLibrary("koin.core").get())
            }
        }
    }

    private val Project.libs
        get() = extensions.getByName("libs") as org.gradle.api.artifacts.VersionCatalog
}
```

**Purpose:** Adds Koin dependency injection to any module.

**What it does:**
- Adds Koin Android dependencies
- Adds Koin Compose integration

#### Plugin 6: AndroidFirebaseConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/AndroidFirebaseConventionPlugin.kt`

```kotlin
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
                add("implementation", platform(libs.findLibrary("firebase.bom").get()))
                add("implementation", libs.findLibrary("firebase.crashlytics").get())
                add("implementation", libs.findLibrary("firebase.analytics").get())
            }
        }
    }

    private val Project.libs
        get() = extensions.getByName("libs") as org.gradle.api.artifacts.VersionCatalog
}
```

**Purpose:** Configures Firebase services (Crashlytics, Analytics).

**What it does:**
- Applies Google Services plugin
- Applies Firebase Crashlytics plugin
- Adds Firebase dependencies

**Note:** Make sure your `libs.versions.toml` has these Firebase entries:
```toml
[libraries]
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-analytics = { group = "com.google.firebase", name = "firebase-analytics" }
firebase-crashlytics = { group = "com.google.firebase", name = "firebase-crashlytics" }
```

#### Plugin 7: AndroidTestConventionPlugin.kt

**Location:** `build-logic/convention/src/main/kotlin/AndroidTestConventionPlugin.kt`

```kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("testImplementation", libs.findLibrary("turbine").get())

                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("mockk.android").get())
            }
        }
    }

    private val Project.libs
        get() = extensions.getByName("libs") as org.gradle.api.artifacts.VersionCatalog
}
```

**Purpose:** Adds testing dependencies to any module.

**What it does:**
- Adds unit testing dependencies (JUnit, MockK, Turbine)
- Adds instrumentation testing dependencies (Espresso)

---

## Integration

### Step 7: Update Main Project Settings

**File:** `settings.gradle.kts` (root)

Add the build-logic as an included build:

```kotlin
pluginManagement {
    includeBuild("build-logic")  // Add this line
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
// ... rest of your modules
```

### Step 8: Sync Project

Run:
```bash
./gradlew --stop
./gradlew build-logic:convention:build
```

This will compile your convention plugins and make them available to your project.

---

## Usage Examples

### Example 1: App Module

**File:** `app/build.gradle.kts`

**Before:**
```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.sultonuzdev.coredroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sultonuzdev.coredroid"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Lots of repeated dependencies...
}
```

**After:**
```kotlin
plugins {
    id("coredroid.android.application")
    id("coredroid.android.compose")
    id("coredroid.android.firebase")
    id("coredroid.koin")
}

android {
    namespace = "com.sultonuzdev.coredroid"

    defaultConfig {
        applicationId = "com.sultonuzdev.coredroid"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // Only app-specific dependencies
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:dashboard"))
    // ... other feature modules
}
```

### Example 2: Feature Module

**File:** `feature/battery/build.gradle.kts`

**Before:**
```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.sultonuzdev.coredroid.feature.battery"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":data:battery"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.navigation.compose)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)

    // All the same Compose dependencies...
}
```

**After:**
```kotlin
plugins {
    id("coredroid.android.feature")
}

android {
    namespace = "com.sultonuzdev.coredroid.feature.battery"
}

dependencies {
    // Only feature-specific dependencies
    implementation(project(":data:battery"))
}
```

### Example 3: Core Library Module

**File:** `core/ui/build.gradle.kts`

```kotlin
plugins {
    id("coredroid.android.library")
    id("coredroid.android.compose")
}

android {
    namespace = "com.sultonuzdev.coredroid.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}
```

### Example 4: Data Module (No Compose)

**File:** `data/battery/build.gradle.kts`

```kotlin
plugins {
    id("coredroid.android.library")
    id("coredroid.koin")
}

android {
    namespace = "com.sultonuzdev.coredroid.data.battery"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.android)
}
```

---

## Testing Your Setup

### Step 9: Verify Everything Works

1. **Clean build:**
   ```bash
   ./gradlew clean
   ```

2. **Build the convention plugins:**
   ```bash
   ./gradlew :build-logic:convention:build
   ```

3. **Apply to a test module:**

   Update one of your modules (e.g., `core/ui/build.gradle.kts`):
   ```kotlin
   plugins {
       id("coredroid.android.library")
       id("coredroid.android.compose")
   }

   android {
       namespace = "com.sultonuzdev.coredroid.core.ui"
   }
   ```

4. **Sync and build:**
   ```bash
   ./gradlew :core:ui:assembleDebug
   ```

---

## Common Issues and Solutions

### Issue 1: "Plugin with id 'coredroid.android.library' not found"

**Solution:**
- Make sure you added `includeBuild("build-logic")` in root `settings.gradle.kts`
- Run `./gradlew --stop` and try again
- Check that `build-logic/settings.gradle.kts` includes the convention module

### Issue 2: "Could not find libs in project"

**Solution:**
- Ensure your `build-logic/settings.gradle.kts` has the version catalog configuration
- Check the path to `libs.versions.toml` is correct (`../gradle/libs.versions.toml`)

### Issue 3: Build errors in convention plugins

**Solution:**
- Make sure all required dependencies are in `libs.versions.toml`
- Verify the `compileOnly` dependencies in `convention/build.gradle.kts`
- Check that you're using the correct Android Gradle Plugin version

### Issue 4: "AndroidExtension not found"

**Solution:**
- Make sure you have these in your root `build.gradle.kts` or `libs.versions.toml`:
  ```kotlin
  android-gradlePlugin = { module = "com.android.tools.build:gradle", version.ref = "androidGradlePlugin" }
  ```

---

## Best Practices

1. **Keep plugins focused**: Each plugin should have a single responsibility
2. **Use composition**: Feature plugin applies library + compose + koin plugins
3. **Centralize configuration**: Use `ProjectConfig.kt` for shared constants
4. **Document your plugins**: Add comments explaining what each plugin does
5. **Version carefully**: Keep build-logic and main project in sync
6. **Test thoroughly**: Apply plugins to modules and build to verify

---

## Next Steps

After setting up your convention plugins:

1. **Migrate existing modules** one by one to use convention plugins
2. **Remove duplicate configuration** from module build files
3. **Add more conventions** as needed (e.g., room, networking, etc.)
4. **Share with your team** and document any project-specific conventions

---

## Summary

You now have a complete build-logic setup that:

✅ Reduces boilerplate in module build files
✅ Ensures consistency across all modules
✅ Makes configuration changes easy (change once, apply everywhere)
✅ Improves build performance with Gradle's caching
✅ Provides type-safe, IDE-supported configuration

Your modules go from 100+ lines of repeated configuration to just a few lines!

---

## Additional Resources

- [Gradle Convention Plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html)
- [Now in Android - Convention Plugins Example](https://github.com/android/nowinandroid/tree/main/build-logic)
- [Kotlin DSL Primer](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

---

**Last Updated:** 2025-01-17
**Project:** CoreDroid Multi-Module Android App
