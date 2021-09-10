package com.vanmo.plugins

import com.vanmo.common.command.Command
import com.vanmo.common.command.CommandExecutionError
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import kotlin.jvm.Throws

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
                throw CommandExecutionError("Unable to remove plugin: ${plugin.name}", ex)
            }
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Any {
        val plugin: Plugin = cast(arguments[0])
        return UnloadPluginCommand(plugin)
    }
}
