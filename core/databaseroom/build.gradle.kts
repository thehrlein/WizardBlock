plugins {
    alias(libs.plugins.wizard.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.core.databaseroom"
}

dependencies {

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // Modules
    implementation(projects.core.interactor)
    implementation(projects.core.entities)
    implementation(projects.core.utils)

    // AndroidX
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Koin
    implementation(libs.koin)

    // Gson
    implementation(libs.google.gson)

    // Logging
    implementation(libs.timber)
}
