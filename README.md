# BookHub

The **BookHub** project is a demo application that showcases modern Android development practices, including **MVI architecture**, **Clean Architecture principles**, **Network calls with Retrofit**, **Hilt for dependency injection**.

---

- Developed using a **declarative approach** with Jetpack Compose.
- Designed for the **latest Android SDK 35** and **Kotlin 2.0** with the Compose compiler.

---

### **Modularized Architecture**

The app is modularized into three distinct layers for better scalability and maintainability:
- **Data module**: Responsible for managing API calls and local data storage.
- **Domain module**: Contains business logic.
- **Presentation module**: Handles UI rendering and user interactions.

###

## Project Highlights

- **Architecture Pattern** :  Implemented **Model-View-Intent (MVI)** architecture pattern to maintain a unidirectional data flow and predictable state management.
- **Clean Architecture Principles** :  Follows Clean Architecture principles to ensure modularity, scalability, and a clear separation of layers.
- **Network Calls**:  Network calls are managed using **Retrofit**. Includes a **common method for safe API calls** to handle errors and responses efficiently.
- **Dependency Injection** :  **Hilt** is used for dependency injection, making it easy to provide and inject dependencies throughout the app.
- **Image Handling** :  Used **Coil** for asynchronous image loading & a **common image picker** with robust permission handling.
- **Reactive Programming** :  Used  **Kotlin Flow** for reactive and asynchronous data streams.
- **Dependency Management** :  All dependencies are managed using a centralized **TOML** file for better maintainability.
- **KSP (Kotlin Symbol Processing)** is used over **KAPT** for improved build performance (Reduces annotation processing overhead).


### Prerequisites
- **Android Studio Koala** or later (Used Android studio Ladybug).
- **JDK 17**.
- A device/emulator running Android 7.0 (API level 24) or higher.

Clone the repository:
   ```bash
   git clone https://github.com/PanktiSP13/jetpack_modular_project_demo.git
   ```

<img src="https://github.com/user-attachments/assets/f88cf162-786d-4118-8ccf-566e6b2214d4" width="250" >
<img src="https://github.com/user-attachments/assets/fd7b35ba-4ad0-4984-90dc-83ddda3cbb16" width="250" >
<img src="https://github.com/user-attachments/assets/9f406318-2546-412f-8a6c-cf81f26a3312" width="250" >


Feel free to explore, use, and contribute to **BookHub** to learn and grow with modern Android development practices! 🚀

