package com.vanmo.test

import com.vanmo.common.command.Command
import com.vanmo.common.plugins.IPlugin
import com.vanmo.resolve

class TestPlugin : IPlugin {

    override fun load() {
        resolve<Command>("IoC.Register", "Test Dependency", TestDependency())()
    }
}
