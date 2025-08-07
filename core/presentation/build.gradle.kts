plugins {
    alias(libs.plugins.wizard.android.library)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.core.presentation"
}

dependencies {

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // Modules
    implementation(projects.core.interactor)
    implementation(projects.core.entities)
    implementation(projects.core.utils)

    // AndroidX
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
