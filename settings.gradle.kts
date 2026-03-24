pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RawgGamesBrowser"

include(":app")
include(":core:common")
include(":core:domain")
include(":core:network")
include(":core:presentation")
include(":core:ui")
include(":feature:games")
