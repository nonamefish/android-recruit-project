// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}

/***
 * Android Gradle Plugin 已經內建了 clean task，不需要再定義 clean task
 *
 * tasks.register<Delete>("clean") {
 *     delete(rootProject.buildDir)
 * }
 */