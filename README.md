# Learning Dashboard - Android Architecture & Design

## 🏗 Architecture
The **Learning Dashboard** application is built following **Clean Architecture** principles layered with the **MVVM (Model-View-ViewModel)** design pattern.

- **Presentation Layer**: Built completely with **Jetpack Compose** and **Material 3**. ViewModels expose UI state using unidirectional data flow with `StateFlow` (`Loading`, `Success`, `Empty`, `Error`).
- **Domain Layer**: Contains immutable domain models (`Course`, `Lesson`), repository interfaces, and use cases (`CalculateProgressUseCase`). Has no framework dependencies.
- **Data Layer**: Implements single source of truth using **Room Database**. Remote data (JSON via Retrofit mock) is synced directly into local sqlite tables (`CourseEntity`, `LessonEntity`) and observed as reactive Kotlin `Flow`s.

---

## 📴 Offline Support
The app implements a **Cache-First / Offline-First** repository policy:
1. On initial load, `CourseRepository` attempts to fetch fresh data from the remote endpoint (assets/fake API).
2. On successful network response, the payload is persisted directly into **Room Database**.
3. If network fails or is unavailable, the repository falls back gracefully to the **Room Database** cache.
4. UI screens collect database updates continuously via `Flow`, ensuring seamless dynamic updates whenever lessons are updated offline.

---

## 🔒 Security
- **Test Credentials**:
  - **Email**: `test@test.com`
  - **Password**: `123456`
- **Authentication**: User credentials are validated using secure input transformations. Secrets/tokens are kept out of shared preferences.
- **Local Database**: Room DB access is encapsulated within repository boundaries. For production, **SQLCipher** can be plugged in for database encryption at rest.
- **Network**: Uses `OkHttp Logging Interceptor` configured strictly for debug builds, enforcing TLS/HTTPS certificates for network traffic.

---

## 🚀 Scaling
- **Feature Modularization**: Packages (`data`, `domain`, `presentation`) can be decoupled into standalone Gradle feature modules (`:core:database`, `:feature:dashboard`).
- **Dependency Injection**: Can easily scale from manual factory injection to **Hilt** or **Koin** as module graph grows.
- **Paging 3**: `LazyColumn` lists can integrate `Paging 3` directly with `Room`'s `RemoteMediator` for large dataset scaling.

---

## 🍏 How the Same App Would Be Built Using SwiftUI
If constructed in iOS with SwiftUI and Swift:
- **Architecture**: Clean Architecture with MVVM using `@ObservableObject` / `@StateObject`.
- **UI State**: Expose enum `ViewState` (`case loading`, `case success([Course])`, `case empty`, `case error(String)`) observed via `@Published`.
- **Database**: Use **SwiftData** or **CoreData** to define `@Model` entities (`CourseEntity`, `LessonEntity`) with relationships.
- **Navigation**: Encase app root in `NavigationStack` with `.navigationDestination(for: Route.self)`.
- **Reactive Streams**: Use **Combine** framework or Swift Async Algorithms / `AsyncSequence` instead of Kotlin `Flow` & `StateFlow`.
