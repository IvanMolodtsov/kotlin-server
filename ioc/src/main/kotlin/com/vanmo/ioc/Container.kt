package com.vanmo.ioc

import com.vanmo.ioc.scope.IScope
import com.vanmo.ioc.scope.RootScope
import com.vanmo.ioc.scope.Scope
import com.vanmo.ioc.scope.init
import kotlin.concurrent.getOrSet

object Container {
    private val scope = ThreadLocal<IScope>()
    private val root = RootScope().apply { init() }

    var currentScope: IScope
        get() {
            return scope.getOrSet { Scope(root) }
        }
        set(value) {
            scope.set(value)
        }
}
