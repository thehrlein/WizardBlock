package plugins.config

import AppBuildConfig
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        AppBuildConfig.flavorDimensions.forEach { dimension ->
            flavorDimensions += dimension.name
            productFlavors {
                dimension.productFlavors.forEach { flavor ->
                    create(flavor.name) {
                        sourceSets {
                            getByName(flavor.name) {
                                resources.setSrcDirs(listOf("src/${flavor.name}/res"))
                            }
                        }
                    }
                }
            }
        }
    }
}
