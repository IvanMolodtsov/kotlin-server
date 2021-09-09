package com.vanmo.ioc

import com.vanmo.ioc.scope.IScope

object Container {
    val currentScope = ThreadLocal<IScope>()

    init {
    }
}
