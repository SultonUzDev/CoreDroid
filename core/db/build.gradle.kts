// ✅ OPTIMIZED with Convention Plugins

plugins {
    id("coredroid.android.library")
}

// ❌ REMOVED - All standard android {} configuration
// ⚠️ WHY: Handled by AndroidLibraryConventionPlugin

dependencies {
    // ✅ Preferences stores model objects

    // ✅ DataStore for preferences
    implementation(libs.datastore.preferences)
}

// 📊 REDUCTION: 42 lines → 7 lines (83% smaller!)
