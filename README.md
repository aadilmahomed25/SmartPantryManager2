# Smart Pantry Manager

A Java Android application for reducing household food waste. Users record ingredients they already have and receive recipe suggestions only when every required ingredient is present.

## Technology
- Java and Android Studio
- SQLite via `SQLiteOpenHelper`
- Android Activities and explicit Intents
- Local data persistence

## Features
- Add, read, update and delete pantry items
- Optional expiry date
- 20 seeded recipes
- Strict recipe matching with basic case, whitespace, accent and plural normalization
- Recipe detail screen with ingredients and preparation steps
- Settings preference stored with Android SharedPreferences
- Empty-state feedback and input validation

## Open and run
1. Install Android Studio and a compatible JDK.
2. Open this folder in Android Studio.
3. Allow Gradle sync to complete.
4. Create or select an Android emulator (API 23 or higher), or connect an Android device with USB debugging enabled.
5. Press **Run** for the `app` configuration.
6. Add ingredients in the Pantry screen and open Suggested Recipes.

## Database
SQLite was selected because the assignment covers local persistent storage, no backend is required, and the app's data is personal to one device. The pantry and seeded recipe records are stored in separate tables.

## Important submission checklist
- Create your own public GitHub repository and push the project in incremental, genuine commits.
- Run the app and capture actual screenshots for the report. Do not use mockups as evidence.
- Record a 5–7 minute narrated video showing the real app and Git history.
- Fill in your name, student number and date in the report and declaration.
