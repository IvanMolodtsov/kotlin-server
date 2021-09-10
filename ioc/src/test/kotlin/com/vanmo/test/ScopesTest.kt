package com.vanmo.test

import com.vanmo.ioc.Container
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.Usable
import com.vanmo.ioc.scope.IScope
import com.vanmo.ioc.scope.MutableScope
import com.vanmo.resolve
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ScopesTest {
    @Test
    fun `ScopeGuard should redo previous scope`() {
        val scope = Container.currentScope

        resolve<Usable>("Scopes.executeInNewScope").use {
            assertNotEquals(scope, Container.currentScope)
        }

        assertEquals(scope, Container.currentScope)
    }

    @Test
    fun `Method set of Scope class should change current scope`() {

        resolve<Usable>("Scopes.executeInNewScope").use {

            assertThrows<ResolveDependencyError> {
                resolve<Int>("dependency")
            }

            val scope = resolve<MutableScope>("Scopes.New")

            scope["dependency"] = { 1 }

            Container.currentScope = scope

            assertEquals(1, resolve("dependency"))
        }
    }

    @Test
    fun `It is possible to create new scope by default`() {
        assertNotNull(resolve<IScope>("Scopes.New"))
    }

    @Test
    fun `Scope exception rollbacks current scope`() {
        val scope = Container.currentScope
        assertThrows<Error> {
            resolve<Usable>("Scopes.executeInNewScope").use {
                assertNotEquals(scope, Container.currentScope)
                throw Error("test")
            }
        }
        assertEquals(scope, Container.currentScope)
    }
}
