import org.gradle.api.JavaVersion

object Dependencies {

    object AndroidX {
        private const val appCompatVersion = "1.4.0"
        private const val coreVersion = "1.7.0"
        private const val constraintLayoutVersion = "2.1.2"
        private const val recyclerViewVersion = "1.2.1"
        private const val splashScreenVersion = "1.0.0-beta01"
        private const val swipeRefreshVersion = "1.1.0"

        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreVersion"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val recyclerView = "androidx.recyclerview:recyclerview:$recyclerViewVersion"
        const val splashScreen = "androidx.core:core-splashscreen:$splashScreenVersion"
        const val swipeToRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshVersion"

        object LifeCycle {
            private const val lifecycleVersion = "2.4.0"

            const val livedataExtensions =
                "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val viewModelExtensions =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        }

        object Navigation {
            const val navigationVersion = "2.4.0-rc01"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
            const val ui = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        }

        object Room {
            private const val roomVersion = "2.4.0"

            const val runtime = "androidx.room:room-runtime:$roomVersion"
            const val extensions = "androidx.room:room-ktx:$roomVersion"
            const val compiler = "androidx.room:room-compiler:$roomVersion"
        }
    }

    object Google {
        private const val googleServicesVersion = "19.0.0"
        private const val gsonVersion = "2.8.6"
        private const val materialVersion = "1.4.0"
        private const val playRatingVersion = "1.8.0"

        const val googleServices =
            "com.google.android.gms:play-services-location:$googleServicesVersion"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
        const val material = "com.google.android.material:material:$materialVersion"
        const val playRating = "com.google.android.play:core:$playRatingVersion"

        object Firebase {
            private const val firebaseVersion = "29.0.3"
            private const val firebaseUiVersion = "8.0.0"
            private const val firebaseAuthPlayServicesVersion = "20.0.0"

            const val bom = "com.google.firebase:firebase-bom:$firebaseVersion"
            const val analytics = "com.google.firebase:firebase-analytics-ktx"
            const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
            const val messaging = "com.google.firebase:firebase-messaging-ktx"
        }
    }

    object Kotlin {
        const val kotlinVersion = "1.6.10"
        private const val coroutineVersion = "1.6.0"
        private const val playKtxVersion = "1.4.1"

        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

        object Coroutine {

            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
            const val playKtx =
                "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$playKtxVersion"
        }
    }

    object Koin {
        private const val koinVersion = "3.1.4"
        const val android = "io.insert-koin:koin-android:$koinVersion"
    }

    object Network {
        private const val okHttp3Version = "4.9.3"
        private const val retrofitVersion = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okHttp3 = "com.squareup.okhttp3:logging-interceptor:$okHttp3Version"
    }

    object Other {
        private const val coilVersion = "1.4.0"
        private const val debugDbVersion = "1.0.6"
        private const val jodaVersion = "2.10.12.2"
        private const val konfettiVersion = "1.2.2"
        private const val lottieVersion = "4.2.2"
        private const val timberVersion = "5.0.1"


        const val coil = "io.coil-kt:coil:$coilVersion"
        const val debugDb = "com.amitshekhar.android:debug-db:$debugDbVersion" // NOT working anymore
        const val joda = "net.danlew:android.joda:$jodaVersion"
        const val konfetti = "nl.dionsegijn:konfetti:$konfettiVersion"
        const val lottie = "com.airbnb.android:lottie:$lottieVersion"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object Test {
        private const val jUnitVersion = "4.13.2"
        private const val jodaTestVersion = "2.10.13"

        const val coroutine =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Kotlin.Coroutine.android}"
        const val joda = "joda-time:joda-time:$jodaTestVersion"
        const val junit = "junit:junit:$jUnitVersion"

        object AndroidX {
            private const val coreTestingVersion = "2.1.0"
            private const val uiAutomatorVersion = "2.2.0"
            private const val rulesVersion = "1.2.0"
            private const val runnerVersion = "1.2.0"
            private const val junitExtensionsVersion = "1.1.1"

            const val core = "androidx.arch.core:core-testing:$coreTestingVersion"
            const val junitExtensions = "androidx.test.ext:junit:$junitExtensionsVersion"
            const val rules = "androidx.test:rules:$rulesVersion"
            const val runner = "androidx.test:runner:$runnerVersion"
            const val uiAutomator = "androidx.test.uiautomator:uiautomator:$uiAutomatorVersion"

            object Espresso {
                private const val espressoVersion = "3.2.0"

                const val core = "androidx.test.espresso:espresso-core:$espressoVersion"
                const val contrib = "androidx.test.espresso:espresso-contrib:$espressoVersion"
            }
        }

        object Mockito {
            private const val androidVersion = "4.2.0"
            private const val kotlinVersion = "2.1.0"

            const val android = "org.mockito:mockito-android:$androidVersion"
            const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$kotlinVersion"
        }
    }
}

object Classpaths {

    private const val gradleVersion = "7.0.4"
    private const val firebaseCrashlyticsVersion = "2.7.0"
    private const val googleServicesVersion = "4.3.8"
    private const val gradleUpdateVersion = "0.28.0"

    const val androidGradlePlugin = "com.android.tools.build:gradle:$gradleVersion"
    const val firebaseCrashlytics =
        "com.google.firebase:firebase-crashlytics-gradle:$firebaseCrashlyticsVersion"
    const val googleServices = "com.google.gms:google-services:$googleServicesVersion"
    const val gradleUpdate = "com.github.ben-manes:gradle-versions-plugin:$gradleUpdateVersion"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Kotlin.kotlinVersion}"
    const val safeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Dependencies.AndroidX.Navigation.navigationVersion}"
}

object BuildPlugins {
    const val detektVersion = "1.19.0"
    const val taskTreeVersion = "1.5"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
    const val googleServices = "com.google.gms.google-services"
    const val gradleUpdater = "com.github.ben-manes.versions"
    const val android = "android"
    const val kapt = "kapt"
    const val projectDependencyGraph =
        "https://raw.githubusercontent.com/JakeWharton/SdkSearch/master/gradle/projectDependencyGraph.gradle"
    const val taskTree = "com.dorongold.task-tree"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
}

object AndroidSdkTools {

    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 31
    const val version_name = "2.3.0"
    const val application_id = "com.tobiashehrlein.tobiswizardblock"
}

object Others {

    const val ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    val JVM_TARGET = JavaVersion.VERSION_1_8.toString()
}

object Module {

    object Framework {

        const val repositories = ":repositories"

        object Database {
            const val room = ":database_room"
        }
    }

    object Ui {
        const val navigation = ":ui_navigation"
        const val gameSettings = ":ui_game_settings"
        const val block = ":ui_block"
        const val savedGames = ":ui_saved_games"
        const val about = ":ui_about"
        const val common = ":ui_common"
    }

    object General {
        const val presentation = ":presentation"
        const val interactor = ":interactor"
        const val entities = ":entities"
        const val utils = ":utils"
    }
}