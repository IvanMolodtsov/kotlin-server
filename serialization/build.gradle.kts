plugins {
    kotlin("plugin.serialization") version "1.5.31"
    `java-library`
    id("com.vanmo.dependency-plugin") version "1.0.0"
}

dependencies {
    import(implementation(project(":ioc"))!!)
    includeJars("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}
