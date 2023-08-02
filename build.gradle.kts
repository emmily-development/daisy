plugins {
  `java-library`
  `maven-publish`
  id("com.github.johnrengelman.shadow") version("8.1.1")
}

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "maven-publish")
  apply(plugin = "com.github.johnrengelman.shadow")

  repositories {
    mavenLocal()
    maven {
      url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
      url = uri("https://repo.codemc.io/repository/nms/")
    }

    maven {
      url = uri("https://repo.maven.apache.org/maven2/")
    }
  }

  group = "dev.emmily"
  version = "0.0.1-SNAPSHOT"

  publishing {
    publications.create<MavenPublication>("maven") {
      from(components["java"])
    }
  }

  tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
  }

  tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
  }

  tasks {
    shadowJar {
      archiveFileName.set("daisy-${project.version}.jar")
      val pkg = "dev.emmily.daisy.ext"

      relocate("com.fasterxml.jackson", "${pkg}.jackson")
    }
  }
}