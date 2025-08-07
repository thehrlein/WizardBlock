plugins {
    alias(libs.plugins.wizard.android.library)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.core.utils"
}

dependencies {

    implementation(projects.core.entities)

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // AndroidX
    implementation(libs.androidx.core.ktx)

    // DateTime
    implementation(libs.joda.android)

    // Logging
    implementation(libs.timber)
}
