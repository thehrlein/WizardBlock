package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugins.config.configureDetektProject

class ProjectConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureDetektProject()
        }
    }
}
