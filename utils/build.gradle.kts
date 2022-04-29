import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Others.JVM_TARGET
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(Dependencies.AndroidX.desugarJdkLibs)

    // AndroidX
    implementation(Dependencies.AndroidX.coreKtx)

    // DateTime
    implementation(Dependencies.Other.joda)

    // Logging
    implementation(Dependencies.Other.timber)

    coreLibraryDesugaring(Dependencies.AndroidX.desugarJdkLibs)

    // Unit Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.joda)
}
