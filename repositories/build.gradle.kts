plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.repositories"
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
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.dataStore)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)

    // Google
    implementation(platform(Dependencies.Google.Firebase.bom))
    implementation(Dependencies.Google.Firebase.analytics)

    // Gson
    implementation(Dependencies.Network.gsonConverter)

    // Kotlin
    implementation(Dependencies.Kotlin.Coroutine.android)
    implementation(Dependencies.Kotlin.Coroutine.playKtx)

    // Koin
    implementation(Dependencies.Koin.android)

    // Logging
    implementation(Dependencies.Other.timber)
}
