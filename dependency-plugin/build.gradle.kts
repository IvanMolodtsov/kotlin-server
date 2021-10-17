plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.5.31-1.0.0")
    implementation(project(":common"))
}

gradlePlugin {
    plugins {
        create("dependency-plugin") {
            id = "com.vanmo.dependency-plugin"
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
