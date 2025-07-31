import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    alias(libs.plugins.wizard.android.application)
    alias(libs.plugins.androidx.safeargs)
    alias(libs.plugins.google.firebase.crashlytics)
}

val buildNumber = Integer.parseInt(
    SimpleDateFormat("yyyyMMddHH", Locale.getDefault()).format(
        Date()
    )
)

android {
    val releaseAlias: String by project.rootProject.ext
    val releaseKeyPassword: String by project.rootProject.ext
    val releaseKeyStorePassword: String by project.rootProject.ext

    namespace = AppBuildConfig.applicationId

    defaultConfig {
        applicationId = AppBuildConfig.applicationId
        minSdk = AppBuildConfig.minSdk
        targetSdk = AppBuildConfig.targetAndCompileSdk
        versionCode = buildNumber
        versionName = AppBuildConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = releaseAlias
            keyPassword = releaseKeyPassword
            storeFile = file("../signing/release_key.jks")
            storePassword = releaseKeyStorePassword
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
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    // Modules
    implementation(projects.feature.navigation)
    implementation(projects.feature.gamesettings)
    implementation(projects.feature.block)
    implementation(projects.feature.savedgames)
    implementation(projects.feature.common)
    implementation(projects.feature.settings)
    implementation(projects.feature.statistics)
    implementation(projects.feature.about)
    implementation(projects.core.repositories)
    implementation(projects.core.databaseroom)
    implementation(projects.core.presentation)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)
    implementation(projects.core.utils)

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash.screen)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.runtime)

    // Logging
    implementation(libs.timber)

    // Google
    implementation(libs.google.material)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.messaging)

    // Koin
    implementation(libs.koin)
}
