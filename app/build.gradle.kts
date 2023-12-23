plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id ("dagger.hilt.android.plugin")
//    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
}

val composeVersion = "1.5.3"
val lifecycleVersion = "2.6.2"
val archVersion = "2.2.0"
val roomVersion = "2.6.1"

android {
    namespace = "com.optiflowx.applekeyboard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.optiflowx.applekeyboard"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
//    implementation ("com.github.dcendents:android-maven-gradle-plugin:2.1")
    androidTestImplementation("androidx.test:runner:1.5.2")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")
    implementation ("androidx.appcompat:appcompat:1.6.1")

    //Compose Dependencies
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.compose.material:material:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation ("androidx.compose.material:material:$composeVersion")
    implementation ("androidx.compose.foundation:foundation:$composeVersion")
    implementation ("androidx.compose.runtime:runtime-livedata:$composeVersion")

    //Activity Compose
    implementation ("androidx.activity:activity-compose:1.8.2")

    //Ui Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    implementation ("androidx.core:core-ktx:1.12.0")
    //    implementation("io.coil-kt:coil-compose:2.5.0")

    //Simplify the UI
    implementation ("com.louiscad.splitties:splitties-systemservices:3.0.0")
    implementation ("com.louiscad.splitties:splitties-views:3.0.0")

    // Compose Preview
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:monitor:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    //Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")


    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Lifecycle Dependencies
    implementation ("androidx.lifecycle:lifecycle-service:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    //Cupertino
    implementation("io.github.alexzhirkevich:cupertino:0.1.0-alpha02")
    implementation("io.github.alexzhirkevich:cupertino-icons-extended:0.1.0-alpha02")

    //Local Storage
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")

    //Room database
    implementation("androidx.room:room-runtime:$roomVersion")
//    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // Dagger Hilt
//    implementation ("com.google.dagger:hilt-android:2.48")
//    ksp("com.google.dagger:hilt-android-compiler:2.48")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")
}