package com.vanmo.ioc.scope

import com.vanmo.ioc.Dependency
import java.util.concurrent.ConcurrentHashMap

class Scope(private var parent: IScope ): MutableScope, LinkedScope {

    private val store = ConcurrentHashMap<String, Dependency<*>>()
    private var notFoundStrategy: (String) -> Dependency<*> = { key -> parent[key] }
    override fun get(key: String): Dependency<*> {
        return store.getOrElse(key) { notFoundStrategy(key) }
    }

    override fun set(key: String, dependency: Dependency<*>) {
        store[key] = dependency
    }

    override fun linkScopes(parent: IScope, notFoundStrategy: (String) -> Dependency<*>) {

    }
}