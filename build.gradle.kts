import java.io.ByteArrayOutputStream

plugins {
   alias(libs.plugins.kotlinJvm) apply false
   alias(libs.plugins.shadow) apply false
   alias(libs.plugins.paperweight) apply false
   alias(libs.plugins.updateDeps) apply true
   alias(libs.plugins.dokka) apply false
   alias(libs.plugins.dokkaJavadoc) apply false
}

allprojects {
   group = "cc.worldmandia"
   version = libraryVersion
}

subprojects {
   apply(plugin = "org.jetbrains.dokka")
   apply(plugin = "org.jetbrains.dokka-javadoc")
}

abstract class GitValueSource : ValueSource<String, GitValueSource.Params> {
   interface Params : ValueSourceParameters {
      val commands: ListProperty<String>
   }

   @get:Inject
   abstract val execOperations: ExecOperations

   override fun obtain(): String? {
      val output = ByteArrayOutputStream()
      val error = ByteArrayOutputStream()
      val commandsList = parameters.commands.get()

      val result = execOperations.exec {
         commandLine("git")
         args(commandsList)
         standardOutput = output
         errorOutput = error
         isIgnoreExitValue = true
      }

      if (result.exitValue != 0) {
         return null
      }
      return output.toString().trim()
   }
}

private fun Project.gitProvider(vararg command: String): Provider<String> {
   return providers.of(GitValueSource::class) {
      parameters.commands.set(command.toList())
   }
}

private val Project.tagProvider: Provider<List<String>>
   get() = gitProvider("tag", "--no-column", "--points-at", "HEAD")
      .map { output ->
         output.takeIf { it.isNotBlank() }?.lines() ?: emptyList()
      }

val Project.commitHash: String
   get() = gitProvider("rev-parse", "--verify", "HEAD").getOrElse("")

val Project.shortCommitHash: String
   get() = gitProvider("rev-parse", "--short", "HEAD").getOrElse("")

val Project.isRelease: Boolean
   get() = providers.gradleProperty("isRelease")
      .map { it.toBoolean() }
      .getOrElse(false)

val Project.libraryVersion: String
   get() {
      val tags = tagProvider.getOrElse(emptyList())

      if (tags.isNotEmpty()) {
         return tags.first()
      }

      val branchName = gitProvider("branch", "--show-current").getOrElse("unknown")
      val nextPlannedVersion = providers.gradleProperty("nextPlannedApiVersion").getOrElse("0.0.1")

      val snapshotPrefix = when (branchName) {
         "master", "main" -> nextPlannedVersion
         else -> branchName.replace('/', '-')
      }

      return if (isRelease) snapshotPrefix else "$snapshotPrefix-SNAPSHOT"
   }