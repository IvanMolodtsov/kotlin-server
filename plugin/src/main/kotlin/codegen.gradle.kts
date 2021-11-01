plugins {
    kotlin("jvm")
    `java-library`
//    kotlin("kapt")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
}

val include: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(include)
    runtimeClasspath.get().extendsFrom(include)

    testCompileClasspath.get().extendsFrom(include)
    testRuntimeClasspath.get().extendsFrom(include)
}

tasks {

    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Dependencies" to ext.properties["imports"],
                    "Main-Class" to "${project.group}.generated.${project.name}"
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
//
// kapt {
//    arguments {
//        arg("project.group", "${project.group}")
//        arg("project.name", project.name)
//    }
// }

ksp {
    arg("project.group", "${project.group}")
    arg("project.name", project.name)
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

sourceSets {
    main {
        java {
            srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    mavenLocal()
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
//    kapt(project(":processor"))
    ksp(project(":processor"))
}
