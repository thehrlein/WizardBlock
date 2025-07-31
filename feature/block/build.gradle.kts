plugins {
    alias(libs.plugins.wizard.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.safeargs)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.feature.block"
}

dependencies {

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // Modules
    implementation(projects.feature.about)
    implementation(projects.feature.settings)
    implementation(projects.feature.common)

    implementation(projects.core.presentation)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)
    implementation(projects.core.utils)

    // AndroidX
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Google
    implementation(libs.google.play.review)

    // Others
    implementation(libs.konfetti)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
