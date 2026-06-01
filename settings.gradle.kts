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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.aliucord.com/releases") }
        maven { url = uri("https://api.xposed.info/") }
    }
}

rootProject.name = "DroidBlox"
include(":app")
include(":aidl")
include(":libdrake")
include(":libdrakepatch")
include(":discordrpc")
include(":logger")
include(":robloxstubs")
