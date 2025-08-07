package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugins.config.configureCommonKotlin

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("org.jetbrains.kotlin.jvm")
            }
            configureCommonKotlin()
        }
    }
}
