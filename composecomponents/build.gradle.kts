plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

val timestamp = System.currentTimeMillis().toString().takeLast(6)
val snapshotVersion = "${project.property("VERSION_MAJOR")}.${project.property("VERSION_MINOR")}.$timestamp-SNAPSHOT"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.sasasoyalan"
            artifactId = "ComposeComponents"
            version = snapshotVersion
            afterEvaluate { from(components["release"]) }
        }
    }
    repositories {
        mavenLocal()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.tooling.preview.android)

    implementation(libs.ui)
    implementation(libs.material3)

    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.compiler)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3.android)

    debugImplementation(libs.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

tasks.register("publishSnapshot") {
    dependsOn("clean", "build", "publishToMavenLocal")
    doLast {
        println("✅ Taze SNAPSHOT versiyonu çıktııı: $snapshotVersion")
    }
}