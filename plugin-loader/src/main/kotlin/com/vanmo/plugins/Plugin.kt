package com.vanmo.plugins

import com.vanmo.common.command.Command
import java.net.URLClassLoader

class Plugin(
    val entryPoint: String,
    val name: String,
    val version: String,
    val dependencies: List<Dependency>,
    internal var loader: URLClassLoader?
) {
    data class Dependency(val name: String, val version: String)

    internal val unloadDependenciesCommands: MutableList<Command> = mutableListOf()
}
