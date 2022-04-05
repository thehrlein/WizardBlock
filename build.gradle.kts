import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
val releaseAlias: String? =
    gradleLocalProperties(rootDir).getProperty("releaseAlias")
        ?: System.getenv("RELEASEALIAS")
        ?: ""
val releaseKeyPassword: String? =
    gradleLocalProperties(rootDir).getProperty("releaseKeyPassword")
        ?: System.getenv("RELEASEKEYPASSWORD")
        ?: ""
val releaseKeyStorePassword: String? =
    gradleLocalProperties(rootDir).getProperty("releaseKeyStorePassword")
        ?: System.getenv("RELEASEKEYSTOREPASSWORD")
        ?: ""
val showLogging: String = gradleLocalProperties(rootDir).getProperty("showLogging", "false")

project.ext {
    set("releaseAlias", releaseAlias)
    set("releaseKeyPassword", releaseKeyPassword)
    set("releaseKeyStorePassword", releaseKeyStorePassword)
    set("showLogging", showLogging)
}
buildscript {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }
    dependencies {
        classpath(Classpaths.androidGradlePlugin)
        classpath(Classpaths.kotlinGradlePlugin)
        classpath(Classpaths.gradleUpdate)
        classpath(Classpaths.safeArgs)
        classpath(Classpaths.googleServices)
        classpath(Classpaths.firebaseCrashlytics)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }
}

plugins {
    id(BuildPlugins.detekt).version(BuildPlugins.detektVersion)
}

dependencies {
    detektPlugins(BuildPlugins.detektFormatting)
}

subprojects {

    apply {
        plugin(BuildPlugins.gradleUpdater)
        plugin(BuildPlugins.detekt)
        from(BuildPlugins.projectDependencyGraph)
    }

    detekt {
        ignoreFailures = true
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        baseline = file("${rootProject.projectDir}/config/detekt/baseline_config.xml")
    }

    afterEvaluate {
        if (hasProperty("android")) {
            (extensions.getByName("android") as com.android.build.gradle.BaseExtension).apply {
                defaultConfig {
                    compileSdkVersion(AndroidSdkTools.compileSdk)
                    defaultConfig {
                        minSdk = AndroidSdkTools.minSdk
                        targetSdk = AndroidSdkTools.targetSdk
                        testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER

                        // The following argument makes the Android Test Orchestrator run its
                        // "pm clear" command after each test invocation. This command ensures
                        // that the app's state is completely cleared between tests.
                        testInstrumentationRunnerArguments["clearPackageData"] = "true"
                        // possibility to colorize vector drawable in xml based on color resources (< API 24)
                        vectorDrawables.useSupportLibrary = true
                    }

                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }

                    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
                        kotlinOptions {
                            jvmTarget = Others.JVM_TARGET
                            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
                        }
                    }
                }
            }
        }
    }

    tasks.withType<DependencyUpdatesTask> {
        // optional parameters
        group = "WizardBlock"
        checkForGradleUpdate = true
        outputFormatter = "json"
        outputDir = "build/dependencyUpdates"
        reportfileName = "report"

        // uncomment if you also want to get alpha beta rc versions
        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.register("clean").configure {
    delete("rootProject.buildDir")
}

val detektAllAutocorrect by tasks.registering(Detekt::class) {
    description = "Runs a failfast detekt build on all modules"
    setSource(file(projectDir))
    debug = true
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = true
    ignoreFailures = true
    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
    baseline.set(file("$projectDir/config/detekt/baseline_config.xml"))
    reports {
        html.enabled = true
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs a detekt build on all modules"
    setSource(file(projectDir))
    debug = true
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = false
    ignoreFailures = false
    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
    baseline.set(file("$projectDir/config/detekt/baseline_config.xml"))
    reports {
        html.enabled = true
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

val detektProjectBaseline by tasks.registering(DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    ignoreFailures.set(true)
    parallel.set(true)
    buildUponDefaultConfig.set(true)
    setSource(files(rootDir))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline_config.xml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}
