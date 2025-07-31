package plugins.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.catalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
