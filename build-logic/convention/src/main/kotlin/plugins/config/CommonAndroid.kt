package plugins.config

import AppBuildConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension

internal fun AndroidComponentsExtension<*, *, *>.configureCommonAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = AppBuildConfig.targetAndCompileSdk

        defaultConfig {
            minSdk = AppBuildConfig.minSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        packaging {
            resources {
                excludes.add("META-INF/*.kotlin_module")
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                excludes.add("META-INF/LICENSE.md") // duplicates in junit jupiter
                excludes.add("META-INF/LICENSE-notice.md") // duplicates in junit jupiter
                excludes.add("META-INF/**MANIFEST.MF")
            }
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }

            unitTests.all {
                it.testLogging {
                    // Always show the result of every unit test, even if it passes.
                    events("passed", "skipped", "failed")
                }
            }
        }
    }
}
