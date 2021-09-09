package com.vanmo.ioc.dependencies

import com.vanmo.ioc.Container
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.ioc.scope.IScope
import com.vanmo.ioc.scope.Scope
import kotlin.jvm.Throws

class ScopeNew : Dependency {

    @Throws(ResolveDependencyError::class)
    override fun invoke(arguments: Array<out Any>): Any {
        val parent: IScope = if (arguments.isNotEmpty()) {
            cast(arguments[0])
        } else {
            Container.currentScope
        }
        return Scope(parent)
    }
}
