plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

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
