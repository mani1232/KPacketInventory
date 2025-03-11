import io.papermc.paperweight.userdev.ReobfArtifactConfiguration.Companion.MOJANG_PRODUCTION

plugins {
    alias(libs.plugins.kotlinJvm) apply true
    alias(libs.plugins.paperweight) apply true
    alias(libs.plugins.shadow) apply true
}

version = "0.0.1-test"

paperweight.reobfArtifactConfiguration = MOJANG_PRODUCTION

dependencies {
    paperweight.paperDevBundle(libs.versions.paperApi)

    implementation(libs.other.packetevents) {
        exclude("net.kyori")
    }
    implementation(libs.kotlin.stdlib)
    implementation(project(":kpi-api"))
    implementation(project(":kpi-platform:kpi-bukkit"))
    implementation(project(":kpi-utils"))
}

kotlin {
    jvmToolchain(21)
}

tasks {
    jar {
        enabled = false
    }
    shadowJar {
        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
        minimize()
        relocate("com.github.retrooper", "${project.group}.libs.packetevents")
        relocate("io.github.retrooper", "${project.group}.libs.packetevents")
    }
}