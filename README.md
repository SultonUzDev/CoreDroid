# CoreDroid Multi-Module Architecture

## Overview

Modern Android architecture for a comprehensive device information app with 24 modules organized by responsibility and feature.

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
├── core/                             # Shared infrastructure (8 modules)
│   ├── common/                       # Utilities, extensions, constants
│   ├── designsystem/                 # Theme, UI components, design system
│   ├── model/                        # Shared domain models
│   ├── data/                         # Base repository/datasource abstractions
│   ├── navigation/                   # Navigation routes and setup
│   ├── preferences/                  # DataStore, user preferences
│   ├── firebase/                    # Firebase Analytics, Crashlytics
│   └── testing/                      # Test utilities, fakes, fixtures
│
├── feature/                          # Feature modules (10 modules)
│   ├── overview/                    # Home screen with quick stats
│   ├── hardware/                     # CPU, GPU, RAM, Storage, Display, Camera
│   ├── battery/                      # Battery monitoring and history
│   ├── monitor/                      # Real-time monitoring with graphs
│   ├── network/                      # WiFi, Cellular, Bluetooth info
│   ├── sensors/                      # Sensor list and real-time data
│   ├── system/                       # Android, kernel, security info
│   ├── tools/                        # Hardware tests and export
│   ├── apps/                         # Installed apps analyzer
│   └── settings/                     # App configuration
│
├── data/                             # Data collection modules (5 modules)
│   ├── device/                       # Hardware data sources
│   ├── battery/                      # Battery data and WorkManager
│   ├── sensors/                      # Sensor data collection
│   ├── network/                      # Network data sources
│   └── system/                       # System info data sources
│
└── sync/                             # Background work (WorkManager)
```

---

## Bottom Navigation (5 Pages)

| Page | Route | Purpose | Key Features |
|------|-------|---------|--------------|
| **Dashboard** | `/dashboard` | Overview & quick stats | Device summary, battery widget, storage chart, RAM usage |
| **Hardware** | `/hardware` | Hardware specifications | CPU, GPU, RAM, Storage, Display, Camera details |
| **Monitor** | `/monitor` | Real-time monitoring | Live CPU/RAM/Battery/Network graphs |
| **Network** | `/network` | Connectivity info | WiFi, Cellular, Bluetooth, Speed test |
| **Tools** | `/tools` | Tests & utilities | Display/Audio/Sensor tests, Export reports |

### Additional Screens
- **Battery** (`/battery`) - Detailed battery monitoring
- **Sensors** (`/sensors`) - Sensor list and details
- **System** (`/system`) - System and device info
- **Apps** (`/apps`) - Installed apps with permissions
- **Settings** (`/settings`) - App preferences

---

## Module Details

### Core Modules

#### `:core:common`
**Purpose**: Shared utilities and base classes

**Contents**:
- Extensions (Context, String, Number, Flow)
- Utils (Constants, DateTimeUtils, FormatUtils)
- Base classes (BaseRepository, BaseViewModel, MviContract)
- Result wrapper for error handling

#### `:core:designsystem`
**Purpose**: Design system and reusable UI components

**Contents**:
- Theme (Color, Type, Dimensions)
- Components (Buttons, Cards, AppBars, Progress)
- Common states (Loading, Error, Empty)
- Material 3 support with dynamic colors

#### `:core:model`
**Purpose**: Shared domain models

**Contents**:
```kotlin
model/
├── DeviceInfo.kt
├── BatteryInfo.kt
├── CpuInfo.kt
├── GpuInfo.kt
├── RamInfo.kt
├── StorageInfo.kt
├── DisplayInfo.kt
├── CameraInfo.kt
├── NetworkInfo.kt
├── SensorInfo.kt
├── SystemInfo.kt
├── AppInfo.kt
└── TestResult.kt
```

#### `:core:data`
**Purpose**: Base data layer abstractions

**Provides**:
- `BaseRepository` with safe API call wrappers
- Flow-based reactive data streams
- Error handling with Result type
- Retry logic for failed operations

#### `:core:navigation`
**Purpose**: Navigation infrastructure

**Contents**:
```kotlin
@Serializable
sealed class NavigationRoute {
    data object Dashboard : NavigationRoute()
    data object Hardware : NavigationRoute()
    data object Battery : NavigationRoute()
    data object Monitor : NavigationRoute()
    data object Network : NavigationRoute()
    data object Sensors : NavigationRoute()
    data class SensorDetail(val sensorType: Int) : NavigationRoute()
    data object System : NavigationRoute()
    data object Tools : NavigationRoute()
    data object Apps : NavigationRoute()
    data object Settings : NavigationRoute()
}
```

#### `:core:preferences`
**Purpose**: User preferences with DataStore

**Settings**:
- Theme (Light/Dark/System)
- Temperature unit (Celsius/Fahrenheit)
- Refresh intervals
- Notification preferences

#### `:core:firebase`
**Purpose**: Analytics and crash reporting, adMob

**Features**:
- Firebase Analytics integration
- Crashlytics for error tracking
- AdMob
- Push notification



#### `:core:testing`
**Purpose**: Shared test utilities

**Provides**:
- Fake repositories and data sources
- Test fixtures with sample data
- Coroutine test rules
- Common test helpers

---

### Feature Modules

Each feature module contains a complete vertical slice:
```
feature/[name]/
├── ui/                    # Composables, ViewModels
├── domain/                # Use cases
├── data/                  # Repository implementations (optional)
└── di/                    # Koin module
```

#### Feature Module Responsibilities

| Module       | What to Display |
|--------------|-----------------|
| **overview** | Device model, Android version, Battery widget, Storage pie chart, RAM bar, CPU temp, Quick stats (resolution, cores, RAM, network) |
| **hardware** | CPU (name, arch, cores, freq, governor), GPU (renderer, OpenGL, Vulkan), RAM (total/used), Storage (internal/external), Display (resolution, DPI, refresh rate), Camera (MP, aperture, features) |
| **battery**  | Level, status, health, temp, voltage, current, capacity, cycle count, history graphs (24h/7d/30d), optimization tips |
| **monitor**  | Real-time CPU freq/usage per core, RAM usage over time, Battery drain/charge rate, Network speed, Temperature zones |
| **network**  | WiFi (SSID, IP, speed, signal), Cellular (operator, type, signal, IMEI), Bluetooth (version, devices), Speed test |
| **sensors**  | List all sensors (accelerometer, gyro, light, etc.), Real-time data, Calibration, Detail screens with graphs |
| **system**   | Device (manufacturer, model), Android (version, API, patch), Kernel (version, arch), Security (Knox, SafetyNet, encryption) |
| **tools**    | Hardware tests (display, audio, sensors, buttons, vibration), Export reports (PDF/JSON), Benchmark |
| **apps**     | Installed apps list, Filters (system/user), Permissions analysis, App details (size, SDK, activities) |
| **settings** | Appearance, monitoring intervals, units, notifications, data export, about |

---

### Data Modules

#### `:data:device`
**Purpose**: Hardware data collection

**Data Sources**:
- `CpuDataSource` - CPU info from /proc/cpuinfo and /sys
- `GpuDataSource` - GPU info from OpenGL
- `RamDataSource` - Memory from ActivityManager
- `StorageDataSource` - Storage from StatFs
- `DisplayDataSource` - Display metrics
- `CameraDataSource` - Camera capabilities

#### `:data:battery`
**Purpose**: Battery monitoring

**Components**:
- `BatteryDataSource` - BatteryManager integration
- `BatteryMonitorWorker` - Background monitoring
- Historical data tracking

#### `:data:sensors`
**Purpose**: Sensor data collection

**Features**:
- Real-time sensor event listening
- All available sensors detection
- Calibration support

#### `:data:network`
**Purpose**: Network information

**Data Sources**:
- `WifiDataSource` - WiFi details
- `CellularDataSource` - Mobile network info
- `NetworkMonitor` - Connectivity changes

#### `:data:system`
**Purpose**: System information

**Data Sources**:
- `SystemInfoDataSource` - Device and Android info
- `BuildInfoDataSource` - Build details

---

### Sync Module

**Purpose**: Background work coordination

**Components**:
- `MonitoringWorker` - Periodic monitoring tasks
- `DataCollectionWorker` - Background data collection
- `SyncManager` - WorkManager coordination

---

## Dependency Rules

### Allowed Dependencies

```
app → features, core, data, sync
features → core, data (their domain only)
data → core
core/designsystem → core/common
core/preferences → core/model
sync → core, data
```

### Forbidden Dependencies

```
core → features ❌
core → data ❌
data → features ❌
features → other features (minimal) ⚠️
```

### Dependency Graph

```
              ┌─────┐
              │ app │
              └──┬──┘
                 │
     ┌───────────┼───────────┐
     │           │           │
     ▼           ▼           ▼
┌─────────┐ ┌────────┐ ┌────────┐
│features │ │  core  │ │  sync  │
└────┬────┘ └───┬────┘ └───┬────┘
     │          │           │
     └──────────┼───────────┘
                │
                ▼
            ┌──────┐
            │ data │
            └──────┘
```

---

## Build Configuration

### settings.gradle.kts

```kotlin
rootProject.name = "CoreDroid"
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
include(":feature:dashboard")
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

// Sync module
include(":sync")
```

### Feature Module Template

```kotlin
// feature/hardware/build.gradle.kts
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sultonuzdev.coredroid.feature.hardware"
    compileSdk = 34
    defaultConfig { minSdk = 27 }
}

dependencies {
    // Core modules
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:navigation"))

    // Data modules
    implementation(project(":data:device"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Koin
    implementation(libs.koin.androidx.compose)

    // Testing
    testImplementation(project(":core:testing"))
}
```

---

## Architecture Pattern

### Clean Architecture + MVI

```
UI (Composables)
    ↓ User Events
ViewModel (MVI - State/Intent/Effect)
    ↓ Use Case invocation
Use Cases (Business Logic)
    ↓ Repository calls
Repositories (Data coordination)
    ↓ Data source access
Data Sources (Android APIs)
```

### MVI Contract Example

```kotlin
object HardwareContract {
    data class State(
        val hardwareInfo: UiState<HardwareInfo> = UiState.Loading
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
|---------------------|-----------|
| **Language**        | Kotlin 2.0.21 |
| **UI**              | Jetpack Compose + Material 3 |
| **Architecture**    | Clean Architecture + MVI |
| **DI**              | Koin 3.5.0 |
| **Async**           | Coroutines + Flow |
| **Navigation**      | Navigation Compose with type safety |
| **Persistence**     | DataStore (Preferences) |
| **Background Work** | WorkManager |
| **Firebase**        | Firebase Analytics + Crashlytics |
| **Logging**         | Timber |
| **Testing**         | JUnit, MockK, Turbine |

---

## Benefits

### Build Performance
- **Incremental builds**: 10-20 seconds (vs 60+ seconds)
- **75-85% faster** than single module
- Parallel compilation of independent modules
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

## Migration Strategy

### Phase 1: Setup (Week 1)
1. Create module directories
2. Update settings.gradle.kts
3. Create build.gradle.kts for all modules

### Phase 2: Core Modules (Week 2)
1. Migrate core/model (no dependencies)
2. Migrate core/common
3. Migrate core/designsystem
4. Migrate core/data, navigation, preferences

### Phase 3: Data Modules (Week 3)
1. Create data/system
2. Create data/device
3. Create data/battery, network, sensors

### Phase 4: Feature Modules (Weeks 4-5)
**Order**: system → hardware → battery → network → sensors → monitor → tools → apps → dashboard → settings

### Phase 5: App Module (Week 6)
1. Wire all modules in CoreDroidApplication
2. Set up navigation graph
3. Test thoroughly

---

## Module Count Summary

- **24 Total Modules**
    - 1 App module
    - 8 Core modules
    - 10 Feature modules
    - 5 Data modules
    - 1 Sync module

- **5 Bottom Navigation Pages**
    - Dashboard, Hardware, Monitor, Network, Tools

- **5 Additional Screens**
    - Battery, Sensors, System, Apps, Settings

---

## Package Naming Convention

```
com.sultonuzdev.coredroid.[module-type].[module-name].[layer]

Examples:
- com.sultonuzdev.coredroid.core.model
- com.sultonuzdev.coredroid.feature.hardware.ui
- com.sultonuzdev.coredroid.data.device.datasource
```

---

## Best Practices

### Module Independence
- Each feature is self-contained
- No direct dependencies between features
- Share via data modules, not feature modules

### API Visibility
- Use `internal` for implementation details
- Expose only necessary public APIs
- Public: Route composables, Koin modules

### Resource Naming
- Prefix with module name: `hardware_screen_title`
- Prevents resource conflicts
- Clear resource ownership

### Testing
- Unit tests per module
- Fake implementations in core/testing
- Integration tests in app module

---

**Document Version:** 3.0 (Clean Architecture)
**Last Updated:** 2025-10-17
**Author:** SultonUzDev with Claude Code
**Project:** CoreDroid Multi-Module Architecture
