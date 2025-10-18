// ✅ OPTIMIZED with Convention Plugins

plugins {
    id("coredroid.android.feature")
    // ✨ This SINGLE plugin provides EVERYTHING:
    // ┌─ coredroid.android.library
    // │  ├─ Android library configuration
    // │  ├─ Auto-namespace (com.sultonuzdev.coredroid.feature.NAME)
    // │  ├─ SDK versions from ProjectConfig
    // │  └─ Base dependencies (core-ktx, timber, testing)
    // ├─ coredroid.android.compose
    // │  ├─ Compose compiler + all UI dependencies
    // │  └─ Lifecycle Compose
    // ├─ coredroid.android.koin
    // │  ├─ Koin Android + Compose
    // │  └─ Koin Test
    // └─ Feature-specific
    //    ├─ Core modules (:core:common, :core:designsystem, :core:model, :core:navigation)
    //    ├─ Navigation Compose
    //    └─ Coroutines
}

// ❌ REMOVED (~40-50 lines):
// - plugins { alias(libs.plugins.android.library) }
// - plugins { alias(libs.plugins.kotlin.android) }
// - plugins { alias(libs.plugins.kotlin.compose) }
// - android { namespace / compileSdk / minSdk / buildTypes / compileOptions / kotlinOptions }
// - dependencies { implementation(libs.androidx.core.ktx) }
// - dependencies { implementation(libs.bundles.compose) }
// - dependencies { implementation(libs.bundles.lifecycle) }
// - dependencies { implementation(libs.bundles.koin) }
// - dependencies { implementation(libs.navigation.compose) }
// - dependencies { implementation(libs.bundles.coroutines) }
// - dependencies { implementation(project(":core:common")) }
// - dependencies { implementation(project(":core:designsystem")) }
// - dependencies { implementation(project(":core:model")) }
// - dependencies { implementation(project(":core:navigation")) }
// - dependencies { testImplementation(project(":core:testing")) }
//
// ⚠️ WHY REMOVED: ALL automatically provided by coredroid.android.feature!

dependencies {
    // ✅ ONLY add feature-specific data module dependencies here
    // Example: implementation(project(":data:device"))
    // Each feature only includes its relevant data modules
}

// 📊 REDUCTION: ~55 lines → ~15 lines (73% smaller!)
// 🎯 BENEFIT: Change once in AndroidFeatureConventionPlugin, applies to all 10 features!
