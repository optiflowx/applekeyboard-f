plugins {
//    kotlin("plugin.serialization")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id ("dagger.hilt.android.plugin")
//    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("androidx.baselineprofile")
}

val hiltVersion = "2.48"
val composeVersion = "1.6.0"
val lifecycleVersion = "2.7.0"
val archVersion = "2.2.0"
val roomVersion = "2.6.1"

android {
    namespace = "com.optiflowx.optikeysx"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.optiflowx.optikeysx"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isProfileable = true //TO BE FALSE
            isCrunchPngs = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isProfileable = true
            isCrunchPngs = false
            isDebuggable = true
            isJniDebuggable = true

            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.profileinstaller:profileinstaller:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout-core:1.0.4")
    androidTestImplementation("androidx.test:runner:1.5.2")

//    implementation ("io.qonversion.android.sdk:sdk:7.1.0") // Qonversion SDK

//    implementation("io.github.hokofly:hoko-blur:1.5.3")

    //Destinations
    implementation("io.github.raamcosta.compose-destinations:core:1.10.0")
    "baselineProfile"(project(":baselineprofiles"))
    ksp("io.github.raamcosta.compose-destinations:ksp:1.10.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("androidx.appcompat:appcompat:1.6.1")

    //Compose Dependencies
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    //Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.core:core-ktx:1.12.0")

    //Simplify the UI
    implementation("com.louiscad.splitties:splitties-systemservices:3.0.0")
    implementation("com.louiscad.splitties:splitties-views:3.0.0")

    // Compose Preview
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:monitor:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    //Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")

    // Lifecycle Dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-service:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    //Cupertino
    implementation("io.github.alexzhirkevich:cupertino:0.1.0-alpha03")
    implementation("io.github.alexzhirkevich:cupertino-icons-extended:0.1.0-alpha03")

    //Local Storage
//    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    implementation("androidx.datastore:datastore-preferences:1.1.0-beta01")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    //Room database
    implementation("androidx.room:room-runtime:$roomVersion")

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
}