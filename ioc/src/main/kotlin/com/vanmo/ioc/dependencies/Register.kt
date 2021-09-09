package com.vanmo.ioc.dependencies

import com.vanmo.common.command.Command
import com.vanmo.common.command.CommandExecutionError
import com.vanmo.ioc.Container
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.ioc.scope.MutableScope
import kotlin.Throws

class Register : Dependency {

    class RegisterCommand(
        private val scope: MutableScope,
        private val key: String,
        private val dependency: Dependency
    ) : Command {

        @Throws(CommandExecutionError::class)
        override fun invoke() {
            try {
                scope[key] = dependency
            } catch (ex: Throwable) {
                throw CommandExecutionError("unable to register $key dependency", ex)
            }
        }
    }

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Any {
        try {
            val scope: MutableScope = cast(Container.currentScope)
            val key: String = cast(arguments[0])
            val dependency: Dependency = cast(arguments[1])
            return RegisterCommand(scope, key, dependency)
        } catch (ex: ResolveDependencyError) {
            throw ex
        } catch (ex: Throwable) {
            throw ResolveDependencyError("unable to register dependency", ex)
        }
    }
}
