// ✅ OPTIMIZED with Convention Plugins

plugins {
    id("coredroid.android.library")
}

// ❌ REMOVED - android {} block and common dependencies
// ⚠️ WHY: Base config from AndroidLibraryConventionPlugin

dependencies {
    // ✅ Testing utilities need model fakes
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    
    // ✅ Testing-specific libraries
    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.turbine)
    implementation(libs.bundles.testing)
}

// 📊 REDUCTION: 42 lines → 10 lines (76% smaller!)
