package com.vanmo.plugin

import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency

class IncludeDependency(private val configurationContainer: ConfigurationContainer) : (Dependency?) -> Dependency? {

    override fun invoke(dependency: Dependency?): Dependency? {
        if (dependency != null) {
//            imports.add("${dependency.group}:${dependency.name}")
        }
        return dependency
    }
}
