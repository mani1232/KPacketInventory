dependencies {
    dokka(project(":kpi-api"))
    dokka(project(":kpi-utils"))
    dokka(project(":kpi-platform:kpi-bukkit"))
}

dokka {
    moduleName.set("KPI")
    dokkaSourceSets.all {
        includes.from("docs/INFO.md")
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://kpi.worldmandia.cc")
            remoteLineSuffix.set("#L")
        }
    }
    pluginsConfiguration.html {
        footerMessage.set("(c) WorldMandia")
    }
}