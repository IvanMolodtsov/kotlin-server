plugins {
    `java-library`
    `maven-publish`
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Main-Class" to "com.vanmo.test.TestPlugin",
//                "Dependencies" to ext.properties["imports"]
            )
        )
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

publishing {
    publications {
        create<MavenPublication>("maven") {

            from(components["java"])
        }
    }
}
