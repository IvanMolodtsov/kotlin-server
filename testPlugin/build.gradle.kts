dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Main-Class" to "com.vanmo.test.TestPlugin"
            )
        )
    }
}
