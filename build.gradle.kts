import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    kotlin("jvm") version "1.5.30" apply false
    java
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
}

allprojects {
    group = "com.vanmo"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        implementation(kotlin("stdlib"))
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
        dependsOn("ktlintFormat")
    }
}

tasks.register<Copy>("initGitHooks"){
    var suffix = "bash"
    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
        suffix = "windows"
    }
    from("scripts/pre-commit-$suffix") {
        rename{it.replaceAfter("pre-commit", "")}
    }
    into (".git/hooks")
}