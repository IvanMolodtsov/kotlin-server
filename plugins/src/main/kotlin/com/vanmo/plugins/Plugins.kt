package com.vanmo.plugins

import com.vanmo.common.command.Command
import com.vanmo.common.plugins.IPlugin
import com.vanmo.common.plugins.PluginLoadError
import com.vanmo.resolve

class Plugins : IPlugin {

    override fun load() {
        try {
            resolve<Command>("IoC.Register", "Plugin.new", PluginNew())()
            resolve<Command>("IoC.Register", "Plugin.load", PluginLoad())()
            resolve<Command>("IoC.Register", "Plugin.unload", PluginUnload())()
            resolve<Command>("IoC.Register", "Memory.clean", CleanMemory())()
        } catch (ex: Throwable) {
            throw PluginLoadError("Plugin module failed to load", ex)
        }
    }
}
