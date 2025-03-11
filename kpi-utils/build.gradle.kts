plugins {
    alias(libs.plugins.kotlinJvm) apply true
}

dependencies {
    api(libs.other.adventure.api)
    api(libs.other.adventure.minimessage)
}

kotlin {
    jvmToolchain(21)
}