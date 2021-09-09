package com.vanmo.ioc.scope

import com.vanmo.ioc.Dependency

interface LinkedScope : IScope {
    fun linkScopes(parent: IScope, notFoundStrategy: (String) -> Dependency<*>)
}
