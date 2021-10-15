package com.vanmo.test

import com.vanmo.common.command.Command
import com.vanmo.ioc.Container
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.Usable
import com.vanmo.plugins.Plugin
import com.vanmo.plugins.Plugins
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
        testJar = Paths.get(System.getProperty("user.dir"), "src/test/resources/testPlugin-1.0.0.jar").toFile()
        Plugins().load()
    }

    @Test
    fun `load plugin test`() {
        resolve<Usable>("Scopes.executeInNewScope").use {
            val plugin: Plugin = resolve("Plugin.new", testJar, javaClass.classLoader)
            assertEquals("com.vanmo.test.TestPlugin", plugin.name)
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
