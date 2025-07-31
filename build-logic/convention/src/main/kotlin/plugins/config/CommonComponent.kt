package plugins.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import plugins.extensions.catalog

internal fun Project.configureCommonComponent() {
    val implementation by configurations

    dependencies {
        implementation(project(":core:ui"))
        implementation(project(":core:common"))
        implementation(project(":core:navigation"))
        implementation(project(":core:designsystem"))
        implementation(project(":core:model"))

        implementation(catalog.findLibrary("hilt.navigationCompose").get())
    }
}
