package com.vanmo.ioc.dependencies

import com.vanmo.ioc.*
import com.vanmo.ioc.scope.IScope
import java.io.Closeable
import kotlin.Throws

class ExecuteInScope : Dependency {

    class ScopeGuard(private val scope: IScope) : Usable {

        private val parent = Container.currentScope

        override fun <R> use(block: (Usable) -> R): R {
            return (this as Closeable).use {
                Container.currentScope = scope
                block(this@ScopeGuard)
            }
        }

        override fun close() {
            Container.currentScope = parent
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(args: Array<out Any>): Any {
        try {
            val scope: IScope = cast(args[0])
            return ScopeGuard(scope)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("Unable to execute in scope", ex)
        }
    }
}
