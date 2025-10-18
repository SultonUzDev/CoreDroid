// ✅ OPTIMIZED with Convention Plugins

plugins {
    id("coredroid.android.library")
    // ✨ Provides: Android library + auto-namespace + SDK config + base dependencies
}

// ❌ REMOVED (~35-40 lines):
// - Manual plugin declarations (android.library, kotlin.android)
// - android { namespace / compileSdk / minSdk / buildTypes / compileOptions / kotlinOptions }
// - Common dependencies (core-ktx, appcompat, material)
// - Test dependencies (junit, espresso)
//
// ⚠️ WHY REMOVED: All handled by AndroidLibraryConventionPlugin
//   - Namespace: Auto-calculated (com.sultonuzdev.coredroid.data.NAME)
//   - SDKs: From ProjectConfig (compileSdk=35, minSdk=27)
//   - Java: Upgraded to 17 (was 11)
//   - BuildConfig: Enabled automatically

dependencies {
    // ✅ Data modules typically depend on:
    implementation(project(":core:model"))    // Domain models
    implementation(project(":core:common"))   // Utilities

    // ✅ Add module-specific Android APIs or libraries here
    // Example for :data:battery:
    // - WorkManager (for background battery monitoring)
    // Example for :data:network:
    // - ConnectivityManager wrapper libraries
}

// 📊 REDUCTION: ~42 lines → ~7 lines (83% smaller!)
// 🎯 PATTERN: All data modules follow same structure (Model + Common + Android APIs)
