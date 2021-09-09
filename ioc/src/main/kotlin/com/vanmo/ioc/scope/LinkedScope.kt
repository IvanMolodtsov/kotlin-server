package com.vanmo.ioc.scope

import com.vanmo.ioc.Dependency

interface LinkedScope : IScope {
    val parent: IScope
    var notFoundStrategy: (String) -> Dependency
}
