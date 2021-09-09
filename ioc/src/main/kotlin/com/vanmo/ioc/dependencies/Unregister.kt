package com.vanmo.ioc.dependencies

import com.vanmo.common.command.Command
import com.vanmo.common.command.CommandExecutionError
import com.vanmo.ioc.Container
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.ioc.scope.MutableScope
import kotlin.Throws

class Unregister : Dependency {

    class UnregisterCommand(
        private val scope: MutableScope,
        private val key: String
    ) : Command {
        @Throws(CommandExecutionError::class)
        override fun invoke() {
            try {
                scope.remove(key)
            } catch (ex: Throwable) {
                throw CommandExecutionError("unable to remove $key dependency", ex)
            }
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(args: Array<out Any>): Any {
        try {
            val scope: MutableScope = cast(Container.currentScope)
            val key: String = cast(args[0])
            return UnregisterCommand(scope, key)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to remove dependency", ex)
        }
    }
}
