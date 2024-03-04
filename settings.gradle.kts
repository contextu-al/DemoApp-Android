pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven ("https://jitpack.io")
        maven ("https://repo.contextu.al/sdk/contextual/" )
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://repo.contextu.al/sdk/contextual/" )
        maven ("https://jitpack.io")
    }
}

rootProject.name = "Contextual"
include(":app")
 