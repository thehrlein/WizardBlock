plugins {
    `kotlin-dsl`
}

group = "com.tobiashehrlein.tobiswizardblock.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        // Android
        register("androidApplication") {
            id = libs.plugins.wizard.android.application.get().pluginId
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.wizard.android.library.get().pluginId
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }

        // Kotlin
        register("kotlinLibrary") {
            id = libs.plugins.wizard.kotlin.library.get().pluginId
            implementationClass = "plugins.KotlinLibraryConventionPlugin"
        }

        // Project
        register("project") {
            id = libs.plugins.wizard.project.get().pluginId
            implementationClass = "plugins.ProjectConventionPlugin"
        }
    }
}
