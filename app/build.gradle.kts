plugins {
    kotlin("plugin.serialization")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("androidx.baselineprofile")
//    id("com.google.protobuf")
}

val hiltVersion = "2.48"
val composeVersion = "1.6.8"
val lifecycleVersion = "2.8.3"
val archVersion = "2.2.0"
val roomVersion = "2.6.1"
val voyagerVersion = "1.1.0-beta02"
val grpcVersion = "1.65.1"
val jetprefVersion = "0.1.0"
val cupertinoVersion = "0.1.0-alpha04"

android {
    namespace = "com.optiflowx.optikeysx"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.optiflowx.optikeysx"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.2-release"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

//    protobuf {
//        protoc {
//            artifact = "com.google.protobuf:protoc:4.26.0"
//        }
//        plugins {
//            create("grpc") {
//                artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
//            }
//        }
//        generateProtoTasks {
//            all().forEach {
//                it.plugins {
//                    create("grpc")
//                }
//                it.builtins {
//                    create("kotlin")
//                }
//            }
//        }
//    }
}

dependencies {

    //GRPC
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-protobuf-lite:$grpcVersion")
    implementation("io.grpc:grpc-okhttp:$grpcVersion")
    implementation("io.grpc:grpc-android:$grpcVersion")

    implementation("androidx.profileinstaller:profileinstaller:1.3.1")
    androidTestImplementation("androidx.test:runner:1.6.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-crashlytics")

    implementation("dev.patrickgold.jetpref:jetpref-datastore-model:$jetprefVersion")
    implementation("dev.patrickgold.jetpref:jetpref-datastore-ui:$jetprefVersion")

    // Destinations
    "baselineProfile"(project(":baselineprofiles"))

    // Voyager
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    implementation("androidx.appcompat:appcompat:1.7.0")

    //Compose Dependencies
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    //Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation("androidx.core:core-ktx:1.13.1")

    //Simplify the UI
    implementation("com.louiscad.splitties:splitties-systemservices:3.0.0")
    implementation("com.louiscad.splitties:splitties-views:3.0.0")

    // Compose Preview and Tests
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    implementation("androidx.test:monitor:1.7.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    //Voice
    implementation("net.java.dev.jna:jna:5.13.0@aar")
    implementation("com.alphacephei:vosk-android:0.3.32")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("dev.gustavoavila:java-android-websocket-client:2.0.1")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+

    //Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    implementation("androidx.constraintlayout:constraintlayout-core:1.1.0-alpha13")

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
    implementation("io.github.alexzhirkevich:cupertino:$cupertinoVersion")
    implementation("io.github.alexzhirkevich:cupertino-icons-extended:$cupertinoVersion")

    //Jetbrains Libraries
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //Room database
    implementation("androidx.room:room-runtime:$roomVersion")

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
}