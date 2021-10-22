rootProject.name = "kotlin-server"
include("common")
include("ioc")
include("plugins")
include("testPlugin")
include("serialization")
include("processor")
include("dependency-plugin")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        mavenLocal()
        gradlePluginPortal()
    }
}
