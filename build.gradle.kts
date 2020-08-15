import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val modid: String = "riptide_fix"
val minecraftVersion: String by extra.properties
val yarnMappings: String by extra.properties
val loaderVersion: String by extra.properties

plugins {
  kotlin("jvm") version "1.4.0"
  id("fabric-loom") version "0.4-SNAPSHOT"
  id("maven-publish")
}

repositories {
  maven("http://maven.fabricmc.net")
  maven("https://libraries.minecraft.net")
  mavenCentral()
  jcenter()
}

dependencies {
  minecraft("com.mojang", "minecraft", minecraftVersion)
  mappings("net.fabricmc", "yarn", yarnMappings, classifier = "v2")

  modCompile("net.fabricmc", "fabric-loader", loaderVersion)
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

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
  targetCompatibility = "1.8"
  sourceCompatibility = "1.8"
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