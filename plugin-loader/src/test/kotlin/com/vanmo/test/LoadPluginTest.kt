package com.vanmo.test

import com.vanmo.common.command.Command
import com.vanmo.common.interfaces.Usable
import com.vanmo.generated.`plugin-loader`
import com.vanmo.ioc.Container
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.plugins.Plugin
import com.vanmo.resolve
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.nio.file.Paths

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoadPluginTest {

    lateinit var testJar: File

    @BeforeAll
    fun init() {
        testJar = Paths.get(System.getProperty("user.dir"), "../testPlugin/build/libs/testPlugin-1.0.0.jar").toFile()
        `plugin-loader`().load()
    }

    @Test
    fun `load plugin test`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            val plugin: Plugin = resolve("Plugin.new", testJar, javaClass.classLoader)
            assertEquals("com.vanmo.generated.testPlugin", plugin.entryPoint)
            assertEquals(2, plugin.dependencies.size)
        }
    }

    @Test
    fun `load plugin dependencies test`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            val plugin: Plugin = resolve("Plugin.new", testJar, javaClass.classLoader)
            resolve<Command>("Plugin.load", plugin, Container.currentScope)()
            assertEquals(2, resolve("Test Dependency", 1, 2))
        }
    }

    @Test
    fun `unload plugin dependencies test`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            val plugin: Plugin = resolve("Plugin.new", testJar, javaClass.classLoader)
            resolve<Command>("Plugin.load", plugin, Container.currentScope)()
            assertEquals(2, resolve("Test Dependency", 1, 2))
            resolve<Command>("Plugin.unload", plugin)
            assertThrows<ResolveDependencyError> {
                resolve("Test Dependency", 1, 2)
            }
        }
    }
}
