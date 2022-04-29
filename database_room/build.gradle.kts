plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
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
    implementation(Dependencies.AndroidX.Room.runtime)
    implementation(Dependencies.AndroidX.Room.extensions)
    kapt(Dependencies.AndroidX.Room.compiler)

    // Kotlin
    implementation(Dependencies.Kotlin.kotlin)

    // Koin
    implementation(Dependencies.Koin.android)

    // Gson
    implementation(Dependencies.Google.gson)

    // Logging
    implementation(Dependencies.Other.timber)
}
