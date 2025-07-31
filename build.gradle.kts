import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.wizard.project)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.androidx.safeargs) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
val releaseAlias: String =
    gradleLocalProperties(rootDir, providers).getProperty("releaseAlias")
        ?: System.getenv("RELEASEALIAS")
        ?: ""
val releaseKeyPassword: String =
    gradleLocalProperties(rootDir, providers).getProperty("releaseKeyPassword")
        ?: System.getenv("RELEASEKEYPASSWORD")
        ?: ""
val releaseKeyStorePassword: String =
    gradleLocalProperties(rootDir, providers).getProperty("releaseKeyStorePassword")
        ?: System.getenv("RELEASEKEYSTOREPASSWORD")
        ?: ""
val showLogging: String = gradleLocalProperties(rootDir, providers).getProperty("showLogging", "false")

project.extra.apply {
    set("releaseAlias", releaseAlias)
    set("releaseKeyPassword", releaseKeyPassword)
    set("releaseKeyStorePassword", releaseKeyStorePassword)
    set("showLogging", showLogging)
}

tasks.register("clean").configure {
    delete("rootProject.buildDir")
}
