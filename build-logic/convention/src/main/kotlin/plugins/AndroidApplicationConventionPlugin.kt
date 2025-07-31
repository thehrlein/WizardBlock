package plugins

import AppBuildConfig
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import plugins.config.configureCommonAndroid
import plugins.config.configureCommonKotlin

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.gms.google-services")
            }

            extensions.configure<ApplicationExtension> {
                buildFeatures {
                    buildConfig = true
                }

                defaultConfig {
                    targetSdk = AppBuildConfig.targetAndCompileSdk
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro",
                    )
                }

                val androidComponentsExtension =
                    extensions.getByType<ApplicationAndroidComponentsExtension>()
                androidComponentsExtension.configureCommonAndroid(commonExtension = this)
                configureCommonKotlin()
            }
        }
    }
}
