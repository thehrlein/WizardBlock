package plugins

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import plugins.config.configureCommonAndroid
import plugins.config.configureCommonKotlin

open class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    // Propagate library specific rules to any consumer
                    consumerProguardFiles("proguard-rules.pro")
                }
                buildFeatures {
                    buildConfig = false
                    viewBinding = true
                    dataBinding = true
                }
                androidResources.enable = true

                configureCommonKotlin()
                val androidComponentsExtension = extensions.getByType<LibraryAndroidComponentsExtension>()
                androidComponentsExtension.configureCommonAndroid(commonExtension = this)
            }
        }
    }
}
