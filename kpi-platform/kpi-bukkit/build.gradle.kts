import io.papermc.paperweight.userdev.ReobfArtifactConfiguration.Companion.MOJANG_PRODUCTION
import java.lang.System.getenv

plugins {
    alias(libs.plugins.kotlinJvm) apply true
    alias(libs.plugins.paperweight) apply true
    `maven-publish`
}

paperweight.reobfArtifactConfiguration = MOJANG_PRODUCTION

dependencies {
    paperweight.paperDevBundle(libs.versions.paperApi)

    api(libs.other.packetevents) {
        exclude("net.kyori")
    }

    implementation(project(":kpi-api"))
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("kpi-bukkit") {
            from(components["kotlin"])
            pom {
                name.set("kpi-bukkit")
                description.set("Packet-based GUI api")
                url.set("https://github.com/mani1232/KPayPal")

                licenses {
                    license {
                        name.set("GNU License")
                        url.set("https://github.com/mani1232/KPacketInventory/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("mani1232")
                        name.set("mani1232")
                        email.set("support@worldmandia.cc")
                        organization.set("WorldMandia")
                        organizationUrl.set("https://worldmandia.cc")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/mani1232/KPacketInventory.git")
                    developerConnection.set("scm:git:ssh://github.com/KPacketInventory.git")
                    url.set("https://github.com/mani1232/KPacketInventory")
                }
            }
        }
    }
    repositories {
        maven {
            name = "WorldMandia"
            url = if (version.toString()
                    .endsWith("SNAPSHOT")
            ) uri("https://repo.worldmandia.cc/snapshots") else uri("https://repo.worldmandia.cc/releases")
            credentials {
                username = getenv("MAVEN_NAME")
                password = getenv("MAVEN_SECRET")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}