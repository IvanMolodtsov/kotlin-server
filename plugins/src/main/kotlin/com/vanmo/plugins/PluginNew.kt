package com.vanmo.plugins

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import java.io.File
import java.net.URLClassLoader
import java.util.jar.Manifest

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
            val entryPoint = attributes.getValue("Main-Class")
            return Plugin(entryPoint, loader)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("Unable to create Plugin.", ex)
        }
    }
}
