plugins {
    `java-library`
    id("com.vanmo.plugin") version "1.0.0"
}

dependencies {
    import(implementation(project(":ioc")))
    include("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}
