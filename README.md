# 🌤️ Weather App — Android (Kotlin)

A native Android weather application built with Kotlin and Jetpack Compose. The app fetches live weather data from a FastAPI backend and displays it on a clean, modern dashboard with city selection and weather cards.

## Overview

This is the mobile client for a full-stack weather data project. It connects to a FastAPI backend (which serves data collected by an automated ETL pipeline and stored in PostgreSQL) and displays current weather conditions for multiple cities.

```
FastAPI Backend (REST API)  ->  Android App (Kotlin + Compose)  ->  User
```

## Features

- Native Android UI built with Jetpack Compose
- City selector with wrapping buttons
- Live weather cards: temperature, feels-like, humidity, wind speed, and condition
- Modern gradient design with glassmorphism-style cards
- Loading and error states
- Connects to a REST API using Retrofit

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Networking | Retrofit + Gson |
| Architecture | ViewModel + State |
| Build | Gradle (Kotlin DSL) |
| IDE | Android Studio |

## Project Structure

```
app/src/main/java/com/weather/app/
├── MainActivity.kt        # The UI (weather screen + cards)
├── WeatherViewModel.kt    # Holds state and calls the API
├── WeatherApi.kt          # Retrofit interface (API endpoints)
├── WeatherModels.kt       # Data model for a weather record
└── RetrofitClient.kt      # Retrofit setup and base URL
```

## Prerequisites

- Android Studio (latest stable version)
- Android SDK (API 24+; the app targets a minimum of Android 7.0)
- An Android emulator or a physical Android device
- The FastAPI weather backend running and reachable

## Backend Connection

The app expects the backend REST API to be available. By default it connects to:

```
http://10.0.2.2:8000/
```

`10.0.2.2` is the Android emulator's special alias for the host machine's `localhost`. This is set in `RetrofitClient.kt`.

If you run on a **physical device** instead of the emulator, change `BASE_URL` in `RetrofitClient.kt` to your computer's local network IP, for example:

```kotlin
private const val BASE_URL = "http://192.168.x.x:8000/"
```

The backend must expose these endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/cities` | List of tracked cities |
| GET | `/weather/{city}` | Latest weather for a city |
| GET | `/weather/{city}/history` | Historical records for a city |

## Setup & Run

### 1. Open the Project

Open the project folder in Android Studio. Wait for the initial Gradle sync to complete (this downloads dependencies and may take a few minutes the first time).

### 2. Start the Backend

Make sure the FastAPI backend is running and reachable on port 8000 before launching the app. Without it, the app will show a "Failed to load" message.

### 3. Set Up a Device

Use either:

- **Emulator:** In Android Studio, open Device Manager, create a virtual device (e.g. Pixel 7, API 34), and start it.
- **Physical device:** Enable Developer Options and USB debugging on your phone, connect it via USB, and allow the debugging prompt.

### 4. Run the App

Select your device from the device dropdown at the top, then click the green Run button. The app builds and launches on the device, showing the weather dashboard with live data.

## Permissions

The app requires internet access, declared in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Because the backend uses plain HTTP (not HTTPS), cleartext traffic is enabled in `AndroidManifest.xml`:

```xml
<application
    android:usesCleartextTraffic="true"
    ... >
```

## How It Works

1. When the app starts, the ViewModel calls the backend's `/cities` endpoint and selects the first city.
2. Selecting a city triggers calls to `/weather/{city}` and `/weather/{city}/history`.
3. Retrofit converts the JSON responses into Kotlin `Weather` objects automatically.
4. The Compose UI observes the ViewModel's state and updates the weather cards whenever the data changes.

## Dependencies

Key libraries (added in `app/build.gradle.kts`):

- `com.squareup.retrofit2:retrofit` — REST API calls
- `com.squareup.retrofit2:converter-gson` — JSON to Kotlin object conversion
- `androidx.lifecycle:lifecycle-viewmodel-compose` — ViewModel support in Compose

## Troubleshooting

- **"CLEARTEXT communication not permitted"** — ensure `android:usesCleartextTraffic="true"` is set in `AndroidManifest.xml`.
- **"Failed to load" / connection refused** — confirm the backend is running, and that `BASE_URL` matches your setup (`10.0.2.2` for emulator, local IP for a physical device).
- **Gradle sync issues** — make sure you have a stable internet connection during the first sync.

## License

This project is for educational purposes.

## 👨‍💻 Author

R. P. T. Sandeepa Dilhara (computer engineer and IT undergraduate student )
