/*
 * This file was generated by the Gradle 'init' task.
 */

rootProject.name = "daisy"
include(":daisy-core")
include(":daisy-demo")
include(":daisy-protocol-v1_8_R3")
project(":daisy-core").projectDir = file("core")
project(":daisy-demo").projectDir = file("demo")
project(":daisy-protocol-v1_8_R3").projectDir = file("protocol/protocol-v1_8_R3")