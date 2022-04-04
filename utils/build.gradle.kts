import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Others.JVM_TARGET
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // AndroidX
    implementation(Dependencies.AndroidX.coreKtx)

    // DateTime
    implementation(Dependencies.Other.joda)

    // Logging
    implementation(Dependencies.Other.timber)

    // Unit Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.joda)
}
