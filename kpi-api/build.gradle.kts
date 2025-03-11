import java.lang.System.getenv

plugins {
    alias(libs.plugins.kotlinJvm) apply true
    alias(libs.plugins.shadow) apply true
    `maven-publish`
}

dependencies {

    api(libs.other.packetevents)

    api(libs.kotlin.stdlib)
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("kpi-api") {
            from(components["kotlin"])
            pom {
                name.set("kpi-api")
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