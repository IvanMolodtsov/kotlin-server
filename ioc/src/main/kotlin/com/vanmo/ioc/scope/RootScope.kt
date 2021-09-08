package com.vanmo.ioc.scope

import com.vanmo.ioc.Dependency
import java.util.concurrent.ConcurrentHashMap

class RootScope: IScope {

    private val store = ConcurrentHashMap<String, Dependency<*>>()

    override fun get(key: String): Dependency<*> {
        return store.getOrElse(key, ){ throw ResolveDependencyError("$key Dependency not registered")}
    }
}