import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val modid: String = "riptide_fix"
val minecraftVersion: String by extra.properties
val yarnMappings: String by extra.properties
val loaderVersion: String by extra.properties
val fabricVersion: String by extra.properties

plugins {
  kotlin("jvm") version "1.3.50"
  id("fabric-loom") version "0.2.5-SNAPSHOT"
  id("parseLangFile") version "1.1:1e3345d"
  id("maven-publish")
}

// Probably don't need some of these.
repositories {
  maven(url = "http://maven.fabricmc.net/")
  maven(url = "https://kotlin.bintray.com/kotlinx")
  maven(url = "https://jitpack.io")
  maven(url = "http://server.bbkr.space:8081/artifactory/libs-release/")
  maven(url = "https://dl.bintray.com/shedaniel/cloth-config-2")
  maven(url = "http://maven.sargunv.s3-website-us-west-2.amazonaws.com/")
  mavenCentral()
  jcenter()
}

dependencies {
  modCompile(group = "net.fabricmc", name = "fabric-loader", version = loaderVersion)
  modCompile(group = "net.fabricmc.fabric-api", name = "fabric-api", version = fabricVersion)

  modCompile(group = "me.shedaniel.cloth", name = "config-2", version = "1.1.1")

  include(group = "me.shedaniel.cloth", name = "config-2", version = "1.1.1")

  modImplementation(group = "io.github.prospector", name = "modmenu", version = "1.7.9+build.+")

  minecraft(group = "com.mojang", name = "minecraft", version = minecraftVersion)
  mappings(group = "net.fabricmc", name = "yarn", version = yarnMappings)
}

sourceSets["main"].java.srcDirs("src/")
sourceSets["main"].resources.srcDirs("resources/")

tasks.withType<ProcessResources> {
  from(sourceSets["main"].resources.srcDirs) {
    include("fabric.mod.json")
    expand("version" to project.version)
  }

  from(sourceSets["main"].resources.srcDirs) {
    exclude("fabric.mod.json")
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

tasks {
  val sourcesJar by creating(Jar::class) {
    archiveClassifier.set("sources")

    from(sourceSets["main"].allSource)
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
  }

  val javadocJar by creating(Jar::class) {
    archiveClassifier.set("javadoc")

    from(project.tasks["javadoc"])
    dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
  }

  artifacts {
    add("archives", sourcesJar)
    add("archives", javadocJar)
  }
}