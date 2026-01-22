# 📚 Book_App

Book_App is a modern Android application written in Kotlin that allows users to search, browse, and save books to favorites.  
The app is built with Jetpack Compose and follows modern Android development practices.

---

## 🚀 Features

- 📖 Search and browse books from a remote API  
- 🔍 View detailed information about a selected book  
- ⭐ Add and remove books from favorites  
- 📂 View favorite books in a separate tab  
- 🎨 Fully declarative UI with Jetpack Compose  
- 🌐 Load data from the network  
- 💾 Local storage using Room  
- 🧩 Clean and scalable architecture  

---

## 🛠 Tech Stack

- **Kotlin** — primary programming language  
- **Jetpack Compose** — modern declarative UI toolkit  
- **Ktor** — HTTP client for network requests  
- **Room** — local database for favorite books  
- **Koin** — dependency injection framework  
- **Coroutines & Flow** — asynchronous and reactive programming  
- **Gradle (Kotlin DSL)** — build configuration  
- **Android SDK & AndroidX** — core Android libraries  

---

## 🏗 Architecture

The application follows a layered architecture:

- **UI layer** — Jetpack Compose screens (Book list, Book details)  
- **Presentation layer** — ViewModels with StateFlow  
- **Domain layer** — core business models  
- **Data layer**  
  - Remote data source (Ktor API)  
  - Local data source (Room database)  
- **Dependency Injection** — handled with Koin  

---

## 📦 Getting Started

### Prerequisites

- Android Studio (latest stable version)  
- Android SDK  
- Android emulator or physical Android device  

---

### Installation

```bash
git clone https://github.com/Viktoria129-dt/Book_App.git
cd Book_App
