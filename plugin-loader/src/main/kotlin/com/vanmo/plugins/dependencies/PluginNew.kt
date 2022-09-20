package com.vanmo.plugins.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.plugins.Plugin
import java.io.File
import java.net.URLClassLoader
import java.util.jar.Manifest

@IDependency("Plugin.new")
class PluginNew : Dependency {

    override fun invoke(arguments: Array<out Any>): Any {
        try {
            val jarFile: File = cast(arguments[0])
            val classLoader: ClassLoader = cast(arguments[1])
            val loader = URLClassLoader.newInstance(
                arrayOf(jarFile.toURI().toURL()),
                classLoader
            )
            val url = loader.findResource("META-INF/MANIFEST.MF")
            val manifest = Manifest(url?.openStream())
            val attributes = manifest.mainAttributes
            val name = attributes.getValue("Implementation-Title")
            val version = attributes.getValue("Implementation-Version")
            val dependenciesString = attributes.getValue("Dependencies")
            val dependencies: List<Plugin.Dependency> = if (dependenciesString.isNotBlank()) {
                dependenciesString
                    .subSequence(0, dependenciesString.length - 1)
                    .split(";")
                    .map {
                        val parts = it.split(":")
                        Plugin.Dependency(parts[0], parts[1])
                    }
            } else {
                listOf()
            }
            val entryPoint = attributes.getValue("Main-Class")
            return Plugin(entryPoint, name, version, dependencies, loader)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("Unable to create Plugin.", ex)
        }
    }
}
