// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("com.android.library") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    kotlin("plugin.serialization") version "1.9.10" apply true
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
    id("com.android.test") version "8.3.0" apply false
    id("androidx.baselineprofile") version "1.2.4" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
}
