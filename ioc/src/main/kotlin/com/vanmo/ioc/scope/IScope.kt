package com.vanmo.ioc.scope

import com.vanmo.ioc.Dependency

interface IScope {
    operator fun get(key: String): Dependency<*>
}