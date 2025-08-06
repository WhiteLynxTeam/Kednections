// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.23" apply false
}