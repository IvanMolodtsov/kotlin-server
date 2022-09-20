package com.vanmo.plugins.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.common.command.Command
import com.vanmo.common.command.CommandExecutionError
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.plugins.Plugin
import kotlin.jvm.Throws

@IDependency("Plugin.unload")
class PluginUnload : Dependency {

    class UnloadPluginCommand(private val plugin: Plugin) : Command {

        @Throws(CommandExecutionError::class)
        override fun invoke() {
            try {
                plugin.apply {
                    loader?.close()
                    loader = null
                    unloadDependenciesCommands.forEach {
                        it()
                    }
                }
            } catch (ex: Throwable) {
                throw CommandExecutionError("Unable to remove plugin: ${plugin.entryPoint}", ex)
            }
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Any {
        val plugin: Plugin = cast(arguments[0])
        return UnloadPluginCommand(plugin)
    }
}
