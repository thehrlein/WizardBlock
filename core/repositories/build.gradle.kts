plugins {
    alias(libs.plugins.wizard.android.library)
}

android {
    namespace = "com.tobiashehrlein.tobiswizardblock.core.repositories"
}

dependencies {

    // Used to enable Java 8 features for SDK < 26
    coreLibraryDesugaring(libs.android.desugaring)

    // Modules
    implementation(projects.core.interactor)
    implementation(projects.core.entities)
    implementation(projects.core.utils)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Google
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)

    // Kotlin
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.coroutines.play.services)

    // Koin
    implementation(libs.koin)

    // Logging
    implementation(libs.timber)
}
