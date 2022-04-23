import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
    id(BuildPlugins.firebaseCrashlytics)
    id(BuildPlugins.googleServices)
    id(BuildPlugins.safeArgs)
}

val buildNumber = Integer.parseInt(
    SimpleDateFormat("yyyyMMddHH", Locale.getDefault()).format(
        Date()
    )
)

android {
    compileSdk = AndroidSdkTools.compileSdk
    defaultConfig {
        applicationId = AndroidSdkTools.application_id
        minSdk = AndroidSdkTools.minSdk
        targetSdk = AndroidSdkTools.targetSdk
        versionCode = buildNumber
        versionName = AndroidSdkTools.version_name
        testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER
    }

    signingConfigs {
        create("release") {
            keyAlias = project.rootProject.ext["releaseAlias"] as? String
            keyPassword = project.rootProject.ext["releaseKeyPassword"] as? String
            storeFile = file("../signing/release_key.jks")
            storePassword = project.rootProject.ext["releaseKeyStorePassword"] as? String
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "Boolean",
                "SHOW_LOGS",
                project.rootProject.ext.get("showLogging") as String
            )
        }
        getByName("release") {
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
            multiDexEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "Boolean",
                "SHOW_LOGS",
                project.rootProject.ext.get("showLogging") as String
            )
        }
    }

    buildFeatures.apply {
        viewBinding = true
        dataBinding {
            isEnabled = true
        }
    }
}

dependencies {

    // Modules
    implementation(project(Module.Ui.navigation))
    implementation(project(Module.Ui.gameSettings))
    implementation(project(Module.Ui.block))
    implementation(project(Module.Ui.savedGames))
    implementation(project(Module.Ui.common))
    implementation(project(Module.Ui.settings))
    implementation(project(Module.Ui.statistics))
    implementation(project(Module.Ui.about))
    implementation(project(Module.Framework.repositories))
    implementation(project(Module.Framework.Database.room))
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))
    implementation(project(Module.General.utils))

    // AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.splashScreen)
    implementation(Dependencies.AndroidX.swipeToRefresh)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.runtime)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)
    implementation(Dependencies.AndroidX.Room.runtime)

    // Logging
    implementation(Dependencies.Other.timber)

    // Google
    implementation(Dependencies.Google.material)
    implementation(platform(Dependencies.Google.Firebase.bom))
    implementation(Dependencies.Google.Firebase.analytics)
    implementation(Dependencies.Google.Firebase.crashlytics)
    implementation(Dependencies.Google.Firebase.messaging)

    // Koin
    implementation(Dependencies.Koin.android)

    // Testing
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.AndroidX.Espresso.core)
}
