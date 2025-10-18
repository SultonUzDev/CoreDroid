// ✅ OPTIMIZED with Convention Plugins

plugins {
    id("coredroid.android.feature")

}

dependencies {
    // Example:
// implementation(project(":data:device"))
    // Each feature only includes its relevant data modules
}

// 📊 REDUCTION: ~55 lines → ~15 lines (73% smaller!)
// 🎯 BENEFIT: Change once in AndroidFeatureConventionPlugin, applies to all 10 features!
