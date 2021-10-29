package com.vanmo.plugins

import com.vanmo.common.command.Command
import com.vanmo.common.command.CommandExecutionError
import com.vanmo.common.plugins.IPlugin
import com.vanmo.common.interfaces.Usable
import com.vanmo.ioc.scope.MutableScope
import com.vanmo.resolve

class LoadPluginCommand(
    private val clazz: Class<IPlugin>,
    private val plugin: Plugin,
    private val Scope: MutableScope
) : Command {

    @Throws(CommandExecutionError::class)
    override fun invoke() {
        try {
            val pluginLoader: IPlugin = clazz.getConstructor().newInstance()
            resolve<Usable>("Scopes.executeInScope", Scope).use {
                resolve<Command>(
                    "IoC.Register", "IoC.Register", RegisterPluginDependency(plugin)
                )()
                pluginLoader.load()
                resolve<Command>("IoC.Unregister", "IoC.Register")()
            }
        } catch (ex: Throwable) {
            throw CommandExecutionError("Plugin ${plugin.name} loading failed.", ex)
        }
    }
}
