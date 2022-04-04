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

    implementation(project(Module.Ui.about))
    implementation(project(Module.Ui.common))
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))
    implementation(project(Module.General.utils))

    // AndroidX
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.swipeToRefresh)
    implementation(Dependencies.AndroidX.recyclerView)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)

    // Google
    implementation(Dependencies.Google.playRating)

    // Others
    implementation(Dependencies.Other.konfetti)

    // Koin
    implementation(Dependencies.Koin.android)

    // Logging
    implementation(Dependencies.Other.timber)
}