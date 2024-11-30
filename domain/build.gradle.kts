plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.pinu.domain"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = 24
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Room Local Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt for DI
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // GSON converter
    implementation(libs.gson)

    // Viewmodel Scope
    implementation(libs.lifecycle.viewmodel.ktx)
}