plugins {
    alias(libs.plugins.wizard.android.library)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.core.interactor"
}

dependencies {

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // Modules
    implementation(projects.core.entities)

    // AndroidX
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.core.ktx)

    // Logging
    implementation(libs.timber)
}
