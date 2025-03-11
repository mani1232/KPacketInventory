pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
rootProject.name = "KPacketInventory"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.worldmandia.cc/snapshots")
        maven("https://repo.worldmandia.cc/releases")
        maven("https://repo.codemc.io/repository/maven-snapshots/")
        maven("https://repo.codemc.io/repository/maven-releases/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}
include("kpi-api")
include("kpi-utils")
include("kpi-example")

include("kpi-platform")
include("kpi-platform:kpi-bukkit")