# Klivvr Task: City Search App

##  Objective
Develop an Android app to search through **200,000+ cities** with optimized prefix filtering, responsive UI, and seamless Google Maps integration.  
**Focus Areas**: Performance, UX/UI polish, and clean architecture.

---

##  Key Tasks & Requirements

###  Search Functionality
- **Prefix Filtering**:
    - Case-insensitive search matching city names or country codes (e.g., "A" → "Alabama, US"; "s" → "Sydney, AU").
    - **Performance**: Better than linear time (e.g., Trie/O(1) lookups).
- **Real-time Updates**: UI refreshes per keystroke.

### 🖥 UI/UX
- **City List**:
    - Grouped alphabetically (e.g., "Denver, US" before "Sydney, AU").
    - **Sticky Headers**: With connecting lines between letter groups.
- **City Card**:
    - 🇺🇸 Country flag icon.
    - **Title**: `City, CountryCode` (e.g., "Hurzuf, UA").
    - **Subtitle**: Coordinates (lat/lon).
    - **On Tap**: Open location in Google Maps.
- **Animations**:
    - Search bar focus states.
    - Loading/empty/search result transitions.

### ️ Technical Constraints
- **Language**: Kotlin + Jetpack Compose.
- **Dependencies**:
    - Allowed: JSON parsing (e.g., Kotlin Serialization), DI (e.g., Hilt), Jetpack.
    - **Banned**: Databases (local preprocessing allowed).
- **Compatibility**: Android 5.0+ (API 21).
- **Screen Rotation**: State persistence required.

---

##  Implementation Highlights

###  Optimizations
- **Data Structure**: Preprocessed cities into a **Trie** for O(m) prefix searches (m = prefix length).
- **Threading**: Offloaded heavy ops (Trie build) to `Dispatchers.IO`.
- **Debouncing**: Used `Flow` to handle rapid search input changes.

### Architecture
- **MVVM**: Separation of concerns via `ViewModel` + `Composable` screens.
- **Repo Pattern**: Centralized data handling with `CityRepository`.

---

## Techniques :

* Kotlin
* MVI
* Jetpack compose
* Clean architecture
* Dagger with Hilt
* Kotlin Flow
* StateFlow
* unit testing

## Bonus

* unit testing

### Sources

* https://developer.android.com/jetpack/guide

---

##  Installation & Usage

### Prerequisites
- Android Studio (latest stable version).
- Device/emulator with API 21+.

## Photos

<img src="/Images/1.png" width=260 height=500  title=""> <img src="/Images/2.png" width=260 height=500 title="" > 
<img src="/Images/3.png" width=260 height=500  title="">
### Steps
1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/klivvr-city-search.git