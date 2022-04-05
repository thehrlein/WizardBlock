import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
    id(BuildPlugins.safeArgs)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Others.JVM_TARGET
    }
}

android {
    buildFeatures {
        viewBinding = true
        dataBinding {
            isEnabled = true
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))
    implementation(project(Module.General.utils))

    // Google
    implementation(Dependencies.Google.material)

    // AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.splashScreen)
    implementation(Dependencies.AndroidX.swipeToRefresh)
    implementation(Dependencies.AndroidX.recyclerView)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)

    // Koin
    implementation(Dependencies.Koin.android)

    // Images
    implementation(Dependencies.Other.coil)

    // Logging
    implementation(Dependencies.Other.timber)

    // DateTime
    implementation(Dependencies.Other.joda)

    // Unit Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.joda)
    testImplementation(Dependencies.Test.Mockito.android)
}
