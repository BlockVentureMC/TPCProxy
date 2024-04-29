import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val fruxzAscendVersion: String by project
val fruxzStackedVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0-RC1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.liamxsage"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://nexus.flawcra.cc/repository/maven-mirrors/")
}

val deps = listOf(
    "dev.fruxz:ascend:$fruxzAscendVersion",
    "dev.fruxz:stacked:$fruxzStackedVersion",
)

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")

    deps.forEach {
        implementation(it)
        shadow(it)
    }
}

tasks {

    build {
        dependsOn("shadowJar")
    }

    withType<ProcessResources> {
        filesMatching("velocity-plugin.yml") {
            expand(project.properties)
        }
    }

    withType<ShadowJar> {
        mergeServiceFiles()
        configurations = listOf(project.configurations.shadow.get())
        archiveFileName.set("TPC-Proxy.jar")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        freeCompilerArgs.addAll(listOf("-opt-in=kotlin.RequiresOptIn", "-Xopt-in=dev.kord.common.annotation.KordPreview", "-Xopt-in=dev.kord.common.annotation.KordExperimental", "-Xopt-in=kotlin.time.ExperimentalTime", "-Xopt-in=kotlin.contracts.ExperimentalContracts"))
    }
}