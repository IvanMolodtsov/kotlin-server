package com.vanmo.ioc.scope

import com.vanmo.ioc.Dependency

interface MutableScope: IScope {
    operator fun set(key: String, dependency: Dependency<*>)
}