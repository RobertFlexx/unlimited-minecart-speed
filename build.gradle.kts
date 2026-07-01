import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT"
    kotlin("jvm") version "2.4.0"
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
val loaderVersion = providers.gradleProperty("loader_version").get()
val fabricKotlinVersion = providers.gradleProperty("fabric_kotlin_version").get()
val modVersion = providers.gradleProperty("mod_version").get()
val mavenGroup = providers.gradleProperty("maven_group").get()
val archiveName = providers.gradleProperty("archives_base_name").get()

version = modVersion
group = mavenGroup
base.archivesName.set(archiveName)

repositories {
    // Loom adds the Minecraft/Fabric repositories it needs.
    // Keep these only for Kotlin/Fabric Language Kotlin resolution.
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    // Minecraft 26.1+ is unobfuscated, so there is no Yarn/Mojang mappings dependency here.
    // Using add(...) avoids Kotlin DSL generated-accessor problems with Loom's dynamic configurations.
    add("minecraft", "com.mojang:minecraft:$minecraftVersion")

    // Fabric's 26.1+ template uses normal implementation for loader/API deps.
    implementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
}

loom {
    mods {
        create("unlimited_minecart_speed") {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    // Build with JDK 25, but emit Java 21 bytecode because SpongePowered Mixin
    // currently exposes compatibility levels through JAVA_21. Java 21 bytecode
    // runs fine on the Java 25 runtime required by Minecraft 26.2.
    options.release.set(21)
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
