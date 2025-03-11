<div align="center">
 <h1>KPacketInventory</h1>
 <div>
  <a href="https://discord.worldmandia.cc/">
   <img alt="Discord" src="https://img.shields.io/badge/Discord-WorldMandia-738bd7.svg?style=square" />
  </a>
 </div>
 <br>
</div>

### API that will help you easily create GUI menus based on [PacketEvents](https://github.com/retrooper/packetevents) and written 100% in [Kotlin](https://kotlinlang.org/)

### Installation

Replace `{version}` with the latest version number.

For Snapshots replace `{version}` with `{branch}-SNAPSHOT`

e.g: `feature-amazing-thing-SNAPSHOT` for the branch `feature/amazing-thing`

For Snapshots for the branch `master` replace `{version}` with `{nextPlannedApiVersion}-SNAPSHOT` (see `nextPlannedApiVersion`
in [`gradle.properties`](../gradle.properties))

Per platform feature replace `{platform}` to your platform, like `bukkit`

`implementation("cc.worldmandia:kpi-{platform}:{version}")`

### Gradle (Kotlin)

```kotlin
repositories {
    mavenCentral()
    maven("https://repo.worldmandia.cc/releases")
    // Snapshots Repository (Optional):
    maven("https://repo.worldmandia.cc/snapshots")
}

dependencies {
    implementation("cc.worldmandia:kpi-api:{version}")
}
```

### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://repo.worldmandia.cc/releases"
    }
    // Snapshots Repository (Optional):
    maven {
        url "https://repo.worldmandia.cc/snapshots"
    }
}

dependencies {
    implementation("cc.worldmandia:kpi-api:{version}")
}
```

### Maven

##### Repository:

```xml

<repository>
    <id>snapshots-repo</id>
    <url>https://repo.worldmandia.cc/releases</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
</repository>
```

##### Snapshots Repository (Optional):

```xml

<repository>
    <id>snapshots-repo</id>
    <url>https://repo.worldmandia.cc/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

---

```xml

<dependency>
    <groupId>cc.worldmandia</groupId>
    <artifactId>kpi-api</artifactId>
    <version>{version}</version>
</dependency>
```

### FAQ

**Direct java support?**
- No, we use some kotlin features, but you can create wrapper with builders for support java

**Thread safety?**
- Maybe...
