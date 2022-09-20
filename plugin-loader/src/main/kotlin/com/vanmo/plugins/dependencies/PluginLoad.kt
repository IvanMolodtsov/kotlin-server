package com.vanmo.plugins.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.common.command.Command
import com.vanmo.common.plugins.IPlugin
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.ioc.scope.MutableScope
import com.vanmo.plugins.LoadPluginCommand
import com.vanmo.plugins.Plugin

@IDependency("Plugin.load")
class PluginLoad : Dependency {

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Command {
        @Suppress("UNCHECKED_CAST")
        try {
            val plugin: Plugin = cast(arguments[0])
            val scope: MutableScope = cast(arguments[1])
            val clazz = Class.forName(plugin.entryPoint, true, plugin.loader)
            val newClass = clazz.asSubclass(IPlugin::class.java) as Class<IPlugin>
            return LoadPluginCommand(newClass, plugin, scope)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("Plugin loading failed", ex)
        }
    }
}
