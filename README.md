# 📱 CoreDroid - Complete Device Information App

<div align="center">

![CoreDroid Logo](https://img.shields.io/badge/CoreDroid-Device%20Info-blue?style=for-the-badge&logo=android)

[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.10-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2023.10.01-green.svg?style=flat)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat)](https://opensource.org/licenses/MIT)

**A modern Android app built with Jetpack Compose that displays comprehensive device information including hardware specs, system details, network status, and real-time sensor data.**

[Features](#-features) • [Screenshots](#-screenshots) • [Architecture](#-architecture) • [Getting Started](#-getting-started) • [Contributing](#-contributing)

</div>

---

## 🎯 **Overview**

CoreDroid is a comprehensive device information app that provides detailed insights into your Android device's hardware and software specifications. Built with modern Android development practices, it offers a beautiful Material 3 UI with real-time monitoring capabilities.

### **What Makes CoreDroid Special?**

- 🎨 **Modern UI** - Built entirely with Jetpack Compose and Material 3
- 🏗️ **Clean Architecture** - MVVM + MVI pattern with proper separation of concerns
- 📊 **Real-time Monitoring** - Live sensor data and network monitoring
- 📱 **Comprehensive Info** - Battery, CPU, memory, storage, camera, and system details
- 🔄 **Export Functionality** - Export device reports for sharing
- 🌙 **Dark Mode Support** - Supports both light and dark themes
- ⚡ **Performance Optimized** - Efficient data collection with caching

---

## ✨ **Features**

### 📋 **Device Overview**
- **Quick Stats Dashboard** - Battery level, available storage, RAM, Android version
- **Device Summary** - Manufacturer, model, and key specifications
- **Visual Progress Indicators** - Battery and storage usage with beautiful progress bars

### 🔋 **Battery Information**
- **Real-time Battery Stats** - Level, health, temperature, voltage
- **Charging Status** - Charging source (AC, USB, Wireless)
- **Battery Health Monitoring** - Cycle count and health status
- **Live Monitoring** - Continuous battery status updates

### 💾 **Storage & Memory**
- **Storage Breakdown** - Internal/external storage usage
- **Memory Information** - Total/available/used RAM statistics
- **App Cache Analysis** - Application data and cache sizes
- **Storage Optimization Tips** - Recommendations for freeing space

### 🧠 **CPU & Performance**
- **Processor Details** - CPU name, architecture, core count
- **Frequency Information** - Current and maximum CPU frequencies
- **Performance Metrics** - CPU usage and performance statistics
- **Architecture Support** - ARM64, ARM32 architecture detection

### 📱 **Display Information**
- **Screen Specifications** - Resolution, DPI, size in inches
- **Display Features** - Refresh rate, HDR support, orientation
- **Screen Density** - Detailed density and scaling information

### 📷 **Camera Capabilities**
- **Camera Specifications** - Front and rear camera megapixels
- **Hardware Features** - Flash availability, API level support
- **Multiple Camera Support** - Detection of all available cameras

### 🤖 **System Information**
- **Android Details** - Version, API level, security patch
- **Device Information** - Manufacturer, model, brand, serial number
- **System Security** - Root detection, bootloader status
- **Kernel Information** - Version, build details

### 📡 **Network & Connectivity**
- **Wi-Fi Information** - SSID, IP address, MAC address, signal strength
- **Mobile Network** - Network type (2G/3G/4G/5G), operator, signal strength
- **Connectivity Status** - Bluetooth, NFC, GPS status
- **Real-time Monitoring** - Live network status updates

### 🔬 **Sensor Monitoring**
- **Available Sensors** - Complete list of device sensors
- **Real-time Data** - Live sensor readings with timestamps
- **Sensor Details** - Vendor, power consumption, accuracy, resolution
- **Interactive Monitoring** - Start/stop real-time sensor tracking

### 📤 **Export & Sharing**
- **Device Reports** - Generate comprehensive device reports
- **Multiple Formats** - Export as text files or PDF documents
- **Easy Sharing** - Share reports via email, messaging, or cloud storage

---

## 📱 **Screenshots**

<div align="center">

| Overview Screen | Hardware Details | System Information |
|:---------------:|:----------------:|:------------------:|
| ![Overview](screenshots/overview.png) | ![Hardware](screenshots/hardware.png) | ![System](screenshots/system.png) |

| Network Status | Sensor Monitoring | Export Report |
|:--------------:|:-----------------:|:-------------:|
| ![Network](screenshots/network.png) | ![Sensors](screenshots/sensors.png) | ![Export](screenshots/export.png) |

</div>

---

## 🏗️ **Architecture**

CoreDroid follows **Clean Architecture** principles with **MVVM + MVI** pattern for robust, maintainable, and testable code.

### **Architecture Layers**

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Compose   │  │ ViewModels  │  │    Navigation       │  │
│  │     UI      │  │   (MVI)     │  │      Graph          │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Use Cases  │  │ Repository  │  │      Models         │  │
│  │ (Business   │  │ Interfaces  │  │   (Pure Kotlin)     │  │
│  │   Logic)    │  │             │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ Repository  │  │ Data Sources│  │    Local Storage    │  │
│  │Implementations│  │  (Android   │  │ (Room + DataStore) │  │
│  │             │  │    APIs)    │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### **Key Architectural Components**

- **🎨 Presentation Layer**
  - Jetpack Compose for modern UI
  - MVVM + MVI for state management
  - Type-safe navigation with Navigation Compose

- **🎯 Domain Layer**
  - Use cases for business logic
  - Repository interfaces for data abstraction
  - Pure Kotlin models without Android dependencies

- **📊 Data Layer**
  - Repository implementations
  - Data sources for Android system APIs
  - Room database for caching
  - DataStore for preferences

### **Design Patterns Used**

- **MVVM + MVI** - Unidirectional data flow with predictable state management
- **Repository Pattern** - Data access abstraction
- **Dependency Injection** - Koin for IoC container
- **Observer Pattern** - Flow-based reactive programming
- **Factory Pattern** - Use case and repository creation

---

## 🛠️ **Tech Stack**

### **Core Technologies**
- **Kotlin** `1.9.10` - Modern programming language for Android
- **Jetpack Compose** `2023.10.01` - Modern UI toolkit
- **Material 3** - Latest Material Design system
- **Coroutines** `1.7.3` - Asynchronous programming

### **Architecture Components**
- **ViewModel** - UI-related data holder
- **Navigation Compose** - Type-safe navigation
- **Room** `2.6.0` - Local database
- **DataStore** `1.0.0` - Key-value storage

### **Dependency Injection**
- **Koin** `3.5.0` - Lightweight dependency injection

### **Testing**
- **JUnit** `4.13.2` - Unit testing framework
- **MockK** `1.13.8` - Mocking library
- **Turbine** `1.0.0` - Flow testing utilities

### **Additional Libraries**
- **Timber** `5.0.1` - Logging
- **Lottie** `6.1.0` - Animations
- **Coil** `2.5.0` - Image loading
- **iText** `7.2.5` - PDF generation

---

## 🚀 **Getting Started**

### **Prerequisites**

- **Android Studio** Hedgehog or newer
- **JDK** 8 or higher
- **Android SDK** API 24+ (Android 7.0+)
- **Kotlin** 1.9.10+

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/coredroid.git
   cd coredroid
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Sync dependencies**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

### **Project Structure**

```
app/
├── src/main/java/com/sultonuzdev/coredroid/
│   ├── core/                    # Core utilities and base classes
│   │   ├── base/               # Base ViewModel, Repository
│   │   ├── di/                 # Dependency injection modules
│   │   ├── extensions/         # Kotlin extensions
│   │   ├── navigation/         # Navigation setup
│   │   └── utils/              # Utility classes
│   ├── data/                   # Data layer
│   │   ├── datasource/         # Android API data sources
│   │   ├── local/              # Room database, DataStore
│   │   └── repository/         # Repository implementations
│   ├── domain/                 # Domain layer
│   │   ├── model/              # Domain models
│   │   ├── repository/         # Repository interfaces
│   │   └── usecase/            # Business logic use cases
│   └── presentation/           # Presentation layer
│       ├── screens/            # Feature screens
│       ├── components/         # Reusable UI components
│       └── theme/              # App theming
└── libs.versions.toml          # Dependency versions
```

---

## 🎨 **Design System**

### **Color Palette**
- **Primary**: `#667EEA` → `#764BA2` (Gradient)
- **Secondary**: `#2A5298` → `#1E3C72` (Gradient)
- **Success**: `#28A745` → `#20C997` (Gradient)
- **Error**: `#DC3545`
- **Surface**: `#FFFFFF` / `#1E1E1E` (Dark)

### **Typography**
- **Headers**: SF Pro Display / System Default
- **Body**: Inter / System Default
- **Code**: JetBrains Mono / Monospace

### **Components**
- **Cards**: Rounded corners (16dp), subtle elevation
- **Buttons**: Gradient backgrounds, rounded (12dp)
- **Progress**: Animated progress bars with gradients
- **Status Badges**: Color-coded connection states

---

## 📋 **Permissions**

CoreDroid requires the following permissions to function properly:

### **Required Permissions**
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

### **Optional Permissions**
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

**Note**: The app gracefully handles missing permissions and shows appropriate fallback information.



## 🤝 **Contributing**

We welcome contributions! Please follow these steps:

### **How to Contribute**

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes**
4. **Add tests** for new functionality
5. **Commit your changes**
   ```bash
   git commit -m 'Add some amazing feature'
   ```
6. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
7. **Open a Pull Request**

### **Code Style**
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use [ktlint](https://ktlint.github.io/) for code formatting
- Write meaningful commit messages
- Add documentation for public APIs

### **Development Guidelines**
- Maintain clean architecture principles
- Write comprehensive tests
- Follow Material 3 design guidelines
- Ensure backward compatibility (API 27+)

---


---

## 📞 **Contact & Support**

### **Get in Touch**
- **Email**: sultonuzdev@gmail.com
- **GitHub**: [@SultonUzDev](https://github.com/sultonuzdev)
- **LinkedIn**: [SultonUzDev](https://linkedin.com/in/sultonuzdev)

### **Issue Reporting**
Found a bug or have a feature request? Please [open an issue](https://github.com/sultonuzdev/coredroid/issues) with:
- Device information
- Android version
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)
---


## 🙏 **Acknowledgments**

- **Android Team** - For the amazing Jetpack Compose toolkit
- **Material Design Team** - For the beautiful Material 3 design system
- **Kotlin Team** - For the modern, expressive programming language
- **Open Source Community** - For the excellent libraries and tools

<div align="center">

**Made with ❤️ by [SultonUzDev](https://github.com/sultonuzdev)**

*If you found this project helpful, please consider giving it a star! ⭐*

</div>


## 📄 **License**

```
MIT License

Copyright (c) 2025 SultonUzDev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```