plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.presentation"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Others.JVM_TARGET
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(Dependencies.AndroidX.desugarJdkLibs)

    // Modules
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))
    implementation(project(Module.General.utils))

    // AndroidX
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)

    // Koin
    implementation(Dependencies.Koin.android)

    // Logging
    implementation(Dependencies.Other.timber)
}
