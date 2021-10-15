plugins {
    kotlin("plugin.serialization") version "1.5.31"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}
