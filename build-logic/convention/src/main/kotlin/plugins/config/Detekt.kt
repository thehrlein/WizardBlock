package plugins.config

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import plugins.extensions.catalog

internal fun Project.configureDetektProject() {
    with(pluginManager) {
        apply("io.gitlab.arturbosch.detekt")
    }

    val detektPlugins by configurations
    dependencies {
        detektPlugins(catalog.findLibrary("detekt.formatting").get())
    }

    tasks.withType<Detekt>().configureEach {
        setSource(projectDir)
        include("**/*.kt", "**/*.kts")
        exclude("**/resources/**", "**/build/**", "**/test/**", "**/androidTest/**")

        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        allRules = false
        parallel = true
        reports {
            html.required.set(true)
            xml.required.set(true)
            md.required.set(false)
            sarif.required.set(false)
            txt.required.set(false)
        }
    }

    tasks.register<Detekt>(name = "detektAutoCorrect") {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        autoCorrect = true
        ignoreFailures = true

        group = "Formatting"
        description = "Runs the detekt formatter on all kotlin sources in this project."
    }

    // There is no `check` task on root project level by default. Therefore Detekt, which is configured on root project
    //  level, would not run when `gradle check` is executed. To include it, a `check` task is explicitly registered.
    tasks.register("check") {
        group = "verification"
    }
}
