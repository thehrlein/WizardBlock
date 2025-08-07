plugins {
    alias(libs.plugins.wizard.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.safeargs)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.feature.common"
}

dependencies {

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // Modules
    implementation(projects.core.presentation)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)
    implementation(projects.core.utils)

    // Google
    implementation(libs.google.material)

    // AndroidX
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash.screen)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Koin
    implementation(libs.koin)

    // Images
    implementation(libs.coil)

    // Logging
    implementation(libs.timber)

    // DateTime
    implementation(libs.joda.android)
}
