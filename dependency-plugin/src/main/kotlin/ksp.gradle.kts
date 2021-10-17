import java.nio.file.Paths

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

tasks {
//    register("resolveEntry") {
//        val entry = project.file(
//            Paths.get(
//                project.buildDir.path,
//                "generated", "ksp", "main", "resources", "EntryPoint.txt"
//            )
//        ).reader().toString()
//        println(
//            project.file(
//                Paths.get(
//                    project.buildDir.path,
//                    "tmp", "jar", "MANIFEST.MF"
//                )
//            ).exists() // writer().append("\nMain-Class: $entry").close()
//        )
//    }

    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Dependencies" to ext.properties["imports"]
                )
            )
        }
    }

//    named("build") {
//        finalizedBy("resolveEntry")
//    }
}

dependencies {
    implementation(project(":common"))
    ksp(project(":processor"))
}
