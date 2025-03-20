plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sasasoyalan.composecomponents"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 //
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.tooling.preview.android)

    implementation(libs.ui) // UI bileşenleri için
    implementation(libs.material3) // Material3 kullanımı için

    // **Kotlin Standart Kütüphanesi**
    implementation(libs.kotlin.stdlib)

    // **Jetpack Compose Compiler (Olmazsa hata verir!)**
    implementation("androidx.compose.compiler:compiler:1.5.15")

    // **Compose UI bağımlılıkları eksikse düzeltilmeli**
    implementation("androidx.compose.ui:ui:1.6.3")
    implementation("androidx.compose.foundation:foundation:1.6.3")
    implementation("androidx.compose.material3:material3:1.2.0")

    // **Testler için bağımlılıklar**
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}