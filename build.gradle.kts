// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.com.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    }
}

plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application) apply false

    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.library) apply false

    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin) apply false

    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.hilt) apply false

    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.secrets.gradle) apply false
}