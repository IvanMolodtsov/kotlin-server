plugins {
    `java-library`
}

val include: MutableList<String?> = MutableList(0) { "" }

dependencies {
    implementation(project(":common")).also { include.add("{group: ${it?.group}, name:${it?.name}, version:${it?.version}}") }
    implementation(project(":ioc")).also { include.add("{group: ${it?.group}, name:${it?.name}, version:${it?.version}}") }
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Main-Class" to "com.vanmo.test.TestPlugin",
                "Dependencies" to include
            )
        )
    }
}
