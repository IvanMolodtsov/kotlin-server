import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    kotlin("jvm") version "1.5.31" apply false
    java
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
}

allprojects {
    group = "com.vanmo"
    version = "1.0.0"

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation(platform("org.junit:junit-bom:5.7.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    ktlint {
        disabledRules.set(setOf("no-wildcard-imports"))
    }

    val includeJars by configurations.creating

    configurations {
        runtimeClasspath.get().extendsFrom(includeJars)
    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
            dependsOn("ktlintFormat")
        }
        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }
}

tasks {
    register<Copy>("copyGitHooks") {
        description = "Copy git hooks from scripts/git-hooks"
        group = "git-hooks"
        from("$rootDir/scripts/git-hooks/") {
            include("**/*.sh")
            rename("(.*).sh", "$1")
        }
        into("$rootDir/.git/hooks")
    }

    register<Exec>("installGitHooks") {
        description = "Installs the pre-commit git hooks from scripts/git-hooks."
        group = "git-hooks"
        onlyIf {
            !Os.isFamily(Os.FAMILY_WINDOWS)
        }
        dependsOn(named("copyGitHooks"))

        workingDir(rootDir)
        commandLine("chmod")
        args("-R", "+x", ".git/hooks/")

        doLast {
            logger.info("Git hooks installed successfully.")
        }
    }

    register<Delete>("deleteGitHooks") {
        group = "git-hooks"
        description = "Delete the pre-commit git hooks."
        delete(fileTree(".git/hooks/"))
    }

    afterEvaluate {
        tasks["clean"].dependsOn(tasks.named("installGitHooks"))
    }
}
