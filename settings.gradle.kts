rootProject.name = "kotlin-server"
include("common")
include("ioc")
include("plugin-loader")
include("testPlugin")
include("serialization")
include("processor")
include("plugin")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        mavenLocal()
        gradlePluginPortal()
    }
}
