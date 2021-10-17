package com.vanmo.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class DependencyPlugin : Plugin<Project> {
    val imports = mutableListOf<String>()

    var entry: String = "none"
    override fun apply(project: Project) {
        project.extensions.extraProperties.set("imports", imports)
        project.extensions.extraProperties.set("entry", entry)

//        project.pluginManager.apply(KspGradleSubplugin::class.java)
        project.plugins.apply("ksp")
//        project.dependencies.add("ksp", "com.vanmo:processor:1.0.0")
        project.dependencies.extensions.add(
            "import",
            IncludeDependency(imports)
        )
//        println("root")
//        println(project.file(Paths.get(project.buildDir.path, "generated", "resources", "EntryPoint.txt")))
    }
}
