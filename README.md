# TaskMaster Android 🤖

<p align="center">
  <img src="screenshots/app-icon.png" alt="TaskMaster Icon" width="120"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/platform-Android%208.0+-brightgreen" alt="Platform"/>
  <img src="https://img.shields.io/badge/Kotlin-1.9-blue" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-1.5-purple" alt="Compose"/>
  <img src="https://img.shields.io/badge/license-MIT-green" alt="License"/>
</p>

## 📖 Overview
TaskMaster is a modern task management application for Android, built with **Kotlin** and **Jetpack Compose**. This project demonstrates **Clean Architecture**, **MVI pattern**, and **offline-first design** in a production-quality Android app.

### Part of Multi-Platform Series
This is the **Android implementation** of TaskMaster. See also:
- [TaskMaster iOS](https://github.com/niraj-kale/taskmaster-ios) - Swift + SwiftUI
- [TaskMaster Flutter](https://github.com/niraj-kale/taskmaster-flutter) - Dart + Flutter
- [TaskMaster React Native](https://github.com/niraj-kale/taskmaster-react-native) - TypeScript + React Native

## ✨ Features
- ✅ User authentication (Email/Password + Google Sign-In)
- ✅ Create, read, update, delete tasks
- ✅ Organize tasks by categories
- ✅ Priority levels and due dates
- ✅ Real-time cloud synchronization
- ✅ Offline-first with automatic sync
- ✅ Search and filter tasks
- ✅ Material 3 design
- ✅ Dynamic color theming

## 📸 Screenshots
<!-- Add screenshots after UI is built -->
Coming soon...

## 🏗️ Architecture
This app follows **Clean Architecture** with **MVI** pattern:
- **Presentation Layer**: Compose UI + ViewModels (MVI)
- **Domain Layer**: Business logic + Use cases
- **Data Layer**: Repositories + Data sources (Firebase + Room)

[See detailed architecture documentation →](ARCHITECTURE.md)

## 🛠️ Tech Stack
| Category | Technology |
|----------|-----------|
| **Language** | Kotlin 1.9+ |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVI + Clean Architecture |
| **Async** | Coroutines + Flow |
| **Local Database** | Room |
| **Backend** | Firebase (Auth + Firestore) |
| **Networking** | Retrofit + OkHttp |
| **Dependency Injection** | Hilt |
| **Testing** | JUnit + MockK + Compose Testing |

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17+
- Android SDK 34+
- Minimum Android 8.0 (API 26)
- Firebase account

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/niraj-kale/taskmaster-android.git
cd taskmaster-android
```

2. **Configure Firebase**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com)
   - Download `google-services.json`
   - Place in `app/` directory

3. **Open in Android Studio**
   - File → Open → Select `taskmaster-android` directory

4. **Build and Run**
   - Select device/emulator
   - Click Run button or `Shift + F10`

## 🧪 Testing

### Run Unit Tests
```bash
# Command line
./gradlew test

# Or in Android Studio: Right-click on test directory → Run Tests
```

### Run UI Tests
```bash
./gradlew connectedAndroidTest
```

### Test Coverage
- Target: 70%+
- Focus: ViewModels, Use Cases, Repositories

## 📁 Project Structure
```
app/src/main/java/com/taskmaster/
├── presentation/
│   ├── screens/          # Compose screens
│   ├── viewmodels/       # ViewModels (MVI)
│   └── components/       # Reusable composables
├── domain/
│   ├── entities/         # Core business models
│   ├── usecases/         # Business logic
│   └── repositories/     # Repository interfaces
├── data/
│   ├── repositories/     # Repository implementations
│   ├── datasources/      # Firebase & Room
│   └── models/           # DTOs & mappers
└── core/
    ├── utils/
    ├── extensions/
    └── constants/
```

## 🎯 Key Learnings
This project showcases:
- Clean Architecture in Android
- MVI pattern with Jetpack Compose
- Kotlin Coroutines & Flow
- Offline-first architecture
- Hilt dependency injection
- Modern Android development practices

## 🔜 Future Enhancements
- [ ] Widget support
- [ ] Wear OS companion app
- [ ] Material You theming
- [ ] App shortcuts
- [ ] Work profile support

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author
**Niraj Kale**
- GitHub: [@niraj-kale](https://github.com/niraj-kale)
- LinkedIn: [nirajkale](https://www.linkedin.com/in/nirajkale/)

## 🙏 Acknowledgments
Part of a multi-platform architecture showcase demonstrating consistent patterns across iOS, Android, Flutter, and React Native.

---

**Note**: This is a portfolio project demonstrating expert-level Android development skills.
