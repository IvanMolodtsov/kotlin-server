

plugins {
    kotlin("jvm")
    `java-library`
    id("com.google.devtools.ksp")
}

kotlin {
    sourceSets.main {
        allSource.srcDir("build/generated/ksp/main/kotlin")
    }
}

val include: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(include)
    runtimeClasspath.get().extendsFrom(include)
}

tasks {

    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Dependencies" to ext.properties["imports"],
                    "Main-Class" to "${project.group}.${project.name}"
                )
            )
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(
            configurations["include"].map {
                if (it.isDirectory) it else zipTree(it)
            }
        )
    }
}

ksp {
    arg("project-name", project.name)
    arg("project-group", project.group as String)
}

dependencies {
    implementation(project(":common"))
    ksp(project(":processor"))
}
