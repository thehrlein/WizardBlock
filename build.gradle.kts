import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.

val showLogging: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
    rootDir
).getProperty("showLogging", "false")

project.ext {
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

subprojects {

    apply {
        plugin(BuildPlugins.gradleUpdater)
//        plugin(BuildPlugins.detekt)
        from(BuildPlugins.projectDependencyGraph)
    }

//    detekt {
//        ignoreFailures = true
//        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
//        baseline = file("${rootProject.projectDir}/config/detekt/baseline-config.xml")
//    }

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

