pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
    }
}

val requiredJavaVersion = JavaVersion.VERSION_21
check(JavaVersion.current().isCompatibleWith(requiredJavaVersion)) {
    """
    AFKCSS requires JDK $requiredJavaVersion+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}

buildCache {
    local {
        directory = File(rootDir, ".gradle-build-cache")
    }
}

// enables to use projects accessors in build.gradle files like implementation(projects.core.model)
// which make it possible to use auto-completion and faster navigation with CMD/CTRL + click
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "WizardBlock"

include(":app")

// ui
include(":feature:navigation")
include(":feature:gamesettings")
include(":feature:block")
include(":feature:savedgames")
include(":feature:about")
include(":feature:settings")
include(":feature:statistics")
include(":feature:common")

// framework modules
include(":core:databaseroom")
include(":core:repositories")

// basic architecture modules
include(":core:presentation")
include(":core:interactor")
include(":core:entities")
include(":core:utils")
