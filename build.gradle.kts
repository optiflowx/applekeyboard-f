// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(
            uri("https://jitpack.io")
        )
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")

    }
}

plugins {
    id ("com.android.application") version "8.2.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.10" apply false
//    kotlin("plugin.serialization") version "1.9.10" apply true
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}
