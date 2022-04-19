plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}
val kotlin_version: String by project
val ksp_version: String by project

dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:$kotlin_version-$ksp_version")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
}
repositories {
    gradlePluginPortal()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "com.vanmo.plugin"
            implementationClass = "com.vanmo.plugin.DependencyPlugin"
        }
    }
}
