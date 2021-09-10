package com.vanmo.plugins

import com.vanmo.common.command.Command
import java.net.URLClassLoader

class Plugin(val name: String, internal var loader: URLClassLoader?) {
    internal val unloadDependenciesCommands: MutableList<Command> = mutableListOf()
}
