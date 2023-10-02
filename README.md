# <center>Daisy</center>

---------------------------------------------------------------------------

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Daisy is a powerful library that allows developers and server owners to
easily create GUIs of any kind for Spigot-based servers (1.8.9 <-> 1.20.1)

---------------------------------------------------------------------------

## Installation

---------------------------------------------------------------------------

Use [Maven](https://maven.apache.org/) or [Gradle](https://gradle.org/) in
order to install Daisy

### Maven

---------------------------------------------------------------------------

```xml
<repositories>
  <repository>
    <id>emmily-public</id>
    <url>https://repo.emmily.dev/repository/emmily-public</url>
  </repository>
</repositories>
```

```xml
<dependencies>
  <dependency>
    <groupId>dev.emmily.daisy</groupId>
    <artifactId>daisy-core</artifactId>
    <version>LATEST</version>
  </dependency>
</dependencies>

```

### Gradle

```kotlin
repositories {
    maven("https://repo.emmily.dev/repository/emmily-public")
}

dependencies {
    implementation("dev.emmily:daisy-core:LATEST")
}
```

---------------------------------------------------------------------------

## Usage

Check the [wiki](https://github.com/emmily-development/daisy/wiki) for 
usage.

---------------------------------------------------------------------------

## Contributing

Contributions are **welcome** as long as they were properly implemented and
tested.
