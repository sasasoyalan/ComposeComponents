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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.tooling.preview.android)

    implementation(libs.ui)
    implementation(libs.material3)

    implementation(libs.kotlin.stdlib)

    implementation("androidx.compose.compiler:compiler:1.5.15")

    implementation("androidx.compose.ui:ui:1.6.3")
    implementation("androidx.compose.foundation:foundation:1.6.3")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(libs.androidx.material3.android)

    debugImplementation("androidx.compose.ui:ui-tooling:1.6.2")

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

tasks.register("updateReadmeVersionAndPush") {
    doLast {
        val stableVersion = "${project.findProperty("VERSION_MAJOR")}.${project.findProperty("VERSION_MINOR")}.${project.findProperty("VERSION_PATCH")}"

        val readmeFile = File(rootProject.rootDir, "README.md")

        if (readmeFile.exists()) {
            val readmeText = readmeFile.readText()

            val updatedReadme = readmeText
                .replace(
                    Regex("""\*\*Current version:\*\* `\d+\.\d+\.\d+`"""),
                    "**Current version:** `$stableVersion`"
                )
                .replace(
                    Regex("""implementation\("com.github.sasasoyalan:ComposeComponents:\d+\.\d+\.\d+"\)"""),
                    """implementation("com.github.sasasoyalan:ComposeComponents:$stableVersion")"""
                )

            readmeFile.writeText(updatedReadme)

            println("✅ README.md updated to version: $stableVersion")

            val process = ProcessBuilder("bash", "-c", """
                git add README.md &&
                git commit -m "📌 Update README.md to version $stableVersion" &&
                git push origin main
            """).start()

            process.inputStream.bufferedReader().forEachLine { println(it) }
            process.waitFor()
        }
    }
}