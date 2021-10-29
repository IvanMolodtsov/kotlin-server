plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(project(":common"))
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

// tasks.jar {
// //    manifest {
// //        attributes(
// //            mapOf(
// // //                "Implementation-Title" to project.name,
// // //                "Main-Class" to "com.vanmo.test.TestPlugin",
// // //                "Dependencies" to ext.properties["imports"]
// //            )
// //        )
// //    }
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
// }
