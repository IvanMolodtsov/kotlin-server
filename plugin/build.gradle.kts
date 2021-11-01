plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.5.31-1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
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
