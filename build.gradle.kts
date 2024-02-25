// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id ("com.android.application") version "8.2.1" apply false
    id ("com.android.library") version "8.2.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.22" apply false
    kotlin("plugin.serialization") version "1.9.10" apply true
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.android.test") version "8.2.1" apply false
    id("androidx.baselineprofile") version "1.2.2" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
//    id("com.google.protobuf") version "0.9.4" apply false
//    id("com.google.dagger.hilt.android") version "2.48" apply false
}
