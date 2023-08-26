/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
  `java-library`
}

dependencies {
  api("com.fasterxml.jackson.core:jackson-annotations:2.13.4")
  runtimeOnly(project(":daisy-protocol-v1_8_R3"))
  compileOnly("org.spigotc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

description = "Daisy core"