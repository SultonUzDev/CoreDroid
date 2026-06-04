# CoreDroid Multi-Module Architecture

## Overview

Modern Android architecture for a comprehensive device information app with 14 modules organized by responsibility and feature.

### Key Principles
- **Clean Architecture**: Clear separation of concerns (Data → Domain → Presentation)
- **MVI Pattern**: Unidirectional data flow with State/Intent/Effect
- **Feature Modules**: Self-contained vertical slices
- **Shared Core**: Common infrastructure and utilities

---

## Module Structure

```
CoreDroid/
├── app/                              # Application entry point
│
├── core/                             # Shared infrastructure (7 modules)
│   ├── common/                       # Utilities, extensions, constants
│   ├── ui/                           # UI components, design system, states
│   ├── domain/                       # Domain models, repositories, use cases
│   ├── data/                         # Data sources, repository implementations
│   ├── db/                           # Database, Room, local persistence
│   ├── navigation/                   # Navigation routes and setup
│   └── firebase/                     # Firebase Analytics, Crashlytics (optional)
│
└── feature/                          # Feature modules (6 modules)
    ├── overview/                     # Home screen with quick stats
    ├── battery/                      # Battery monitoring and history
    ├── network/                      # WiFi, Cellular, Bluetooth info
    ├── system/                       # Android, kernel, security info
    ├── tools/                        # Hardware tests and export
    └── settings/                     # App preferences and configuration
```

---

## Bottom Navigation (5 Pages)

| Page | Route | Purpose | Key Features |
|------|-------|---------|----|
| **Overview** | `/overview` | Device summary & quick stats | Device info, battery status, storage, RAM usage, CPU temp |
| **Battery** | `/battery` | Battery monitoring | Level, health, temp, history graphs, optimization tips |
| **Network** | `/network` | Connectivity info | WiFi, Cellular, Bluetooth details, Speed test |
| **System** | `/system` | System information | Device, Android, Kernel, Security details |
| **Tools** | `/tools` | Tests & utilities | Hardware tests, export reports, benchmark |

### Additional Screens
- **Settings** (`/settings`) - App preferences, themes, monitoring intervals

---

## Module Details

### Core Modules

#### `:core:common`
**Purpose**: Shared utilities and base classes

**Contents**:
- Extensions (Context, String, Number, Flow)
- Utils (Constants, DateTimeUtils, FormatUtils)
- Base classes (BaseRepository, BaseViewModel, MviContract)
- Permissions and file handling utilities

#### `:core:ui`
**Purpose**: Design system and reusable UI components

**Contents**:
- Design tokens (Colors, Typography, Dimensions)
- Reusable Composables (Buttons, Cards, AppBars, Progress)
- Common UI states (Loading, Error, Empty)
- Material 3 support with dynamic colors

#### `:core:domain`
**Purpose**: Business logic, domain models, and repository interfaces

**Contents**:
- Domain models (BatteryInfo, CpuInfo, NetworkInfo, SystemInfo, etc.)
- Repository interfaces for all data sources
- Use cases for business operations
- Type-safe data flow

#### `:core:data`
**Purpose**: Data source implementations and repository implementations

**Contents**:
- Data sources (BatteryDataSource, CpuDataSource, DisplayDataSource, etc.)
- Repository implementations
- API call wrappers with error handling
- Flow-based reactive data streams

#### `:core:db`
**Purpose**: Local persistence and database operations

**Contents**:
- Room database setup
- Entity definitions
- DAO interfaces
- Database migrations

#### `:core:navigation`
**Purpose**: Navigation infrastructure and routing

**Contents**:
```kotlin
@Serializable
sealed class NavigationRoute {
    data object Overview : NavigationRoute()
    data object Battery : NavigationRoute()
    data object Network : NavigationRoute()
    data object System : NavigationRoute()
    data object Tools : NavigationRoute()
    data object Settings : NavigationRoute()
}
```

---

### Feature Modules

Each feature module contains a complete vertical slice:
```
feature/[name]/
├── src/main/java/.../[name]/
│   ├── presentation/        # Composables, ViewModels
│   ├── domain/              # Use cases (optional)
│   └── data/                # Repository implementations (optional)
└── src/main/res/            # Resources
```

#### Feature Module Responsibilities

| Module       | What to Display | Key Components |
|--------------|-----------------|---|
| **overview** | Device summary, battery widget, storage chart, RAM usage, CPU temp | Battery status, Storage info, RAM bar, Quick stats |
| **battery** | Battery level, health, temp, voltage, history graphs | Drain rate, Charge rate, Health status, History charts (24h/7d/30d) |
| **network** | WiFi, Cellular, Bluetooth information | SSID, IP address, Signal strength, Network type, Speed test |
| **system** | System and device information | Manufacturer, Android version, Kernel, Build info, Security features |
| **tools** | Hardware tests and utilities | Display test, Audio test, Sensor test, Export reports |
| **settings** | App configuration and preferences | Theme, Language, Monitoring intervals, Notification settings |

---

## Dependency Rules

### Allowed Dependencies

```
app → features, core
features → core
core:ui → core:common
core:data → core:domain, core:common
core:domain → core:common
core:db → core:domain, core:common
core:navigation → core:common
```

### Forbidden Dependencies

```
core → features ❌
features → other features ❌
data → features ❌
ui → domain ❌
```

### Dependency Graph

```
              ┌─────┐
              │ app │
              └──┬──┘
                 │
         ┌───────┴────────┐
         │                │
         ▼                ▼
    ┌────────────┐   ┌────────┐
    │  features  │   │  core  │
    └────────────┘   └───┬────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
    ┌────────┐   ┌───────────┐   ┌────────┐
    │ common │   │ domain    │   │   db   │
    └────────┘   └───────────┘   └────────┘
```

---

## Build Configuration

### settings.gradle.kts

```kotlin
rootProject.name = "CoreDroid"
include(":app")

// Core modules
include(":core:common")
include(":core:ui")
include(":core:db")
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
```

### Feature Module Template

```kotlin
// feature/battery/build.gradle.kts
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sultonuzdev.coredroid.feature.battery"
    compileSdk = 34
    defaultConfig { minSdk = 27 }
}

dependencies {
    // Core modules
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // DI
    implementation(libs.koin.androidx.compose)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
}
```

---

## Architecture Pattern

### Clean Architecture + MVI

```
UI (Composables)
    ↓ User Events (Intent)
ViewModel (MVI - State/Intent/Effect)
    ↓ Use Case invocation
Use Cases (Business Logic)
    ↓ Repository calls
Repositories (Data coordination)
    ↓ Data source access
Data Sources (Android APIs, Database)
```

### MVI Contract Example

```kotlin
object BatteryContract {
    data class State(
        val batteryInfo: UiState<BatteryInfo> = UiState.Loading
    ) : MviContract.State

    sealed interface Intent : MviContract.Intent {
        data object LoadData : Intent
        data object Refresh : Intent
    }

    sealed interface Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect
    }
}
```

---

## Key Technologies

| Category            | Technology |
|---------------------|----|
| **Language**        | Kotlin 2.0+ |
| **UI**              | Jetpack Compose + Material 3 |
| **Architecture**    | Clean Architecture + MVI |
| **DI**              | Koin |
| **Async**           | Coroutines + Flow |
| **Navigation**      | Navigation Compose (type-safe) |
| **Database**        | Room + DataStore |
| **Background Work** | WorkManager |
| **Logging**         | Timber |
| **Testing**         | JUnit, MockK, Turbine |

---

## Benefits

### Build Performance
- **Incremental builds**: Faster compilation (10-20 seconds vs 60+ seconds)
- **Parallel compilation** of independent modules
- Better build caching

### Code Organization
- Clear feature boundaries
- Self-contained modules
- Easy to locate code
- Reduced cognitive load

### Team Scalability
- Multiple developers per feature
- Fewer merge conflicts
- Independent testing
- Parallel development

### Maintainability
- Easy to add/remove features
- Clear dependencies
- Better code reusability
- Simplified testing

---

## Package Naming Convention

```
com.sultonuzdev.coredroid.[module-type].[module-name].[layer]

Examples:
- com.sultonuzdev.coredroid.core.domain.model
- com.sultonuzdev.coredroid.feature.battery.presentation
- com.sultonuzdev.coredroid.core.data.datasource
```

---

## Best Practices

### Module Independence
- Each feature is self-contained
- No direct dependencies between features
- Share via core modules, not feature-to-feature

### API Visibility
- Use `internal` for implementation details
- Expose only necessary public APIs
- Public: Route composables, Koin modules, domain interfaces

### Resource Naming
- Prefix with module name: `battery_screen_title`
- Prevents resource conflicts
- Clear resource ownership

### Testing
- Unit tests per module
- Fake implementations in core modules
- Integration tests in app module

---

## Module Summary

- **14 Total Modules**
    - 1 App module
    - 7 Core modules (common, ui, domain, data, db, navigation, firebase)
    - 6 Feature modules (overview, battery, network, system, tools, settings)

- **5 Bottom Navigation Pages**
    - Overview, Battery, Network, System, Tools

- **1 Additional Screen**
    - Settings

---

- **Document Version:** 2.0 (Current Structure)
- **Last Updated:** 2025-04-08
- **Author:** SultonUzDev with Claude Code
- **Project:** CoreDroid Multi-Module Architecture