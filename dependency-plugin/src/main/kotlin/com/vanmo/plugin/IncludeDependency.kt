package com.vanmo.plugin

import org.gradle.api.artifacts.Dependency
import org.gradle.api.reflect.HasPublicType
import org.gradle.api.reflect.TypeOf

open class IncludeDependency(private val imports: MutableList<String>) : HasPublicType, (Dependency?) -> Dependency? {

    private val type = object : TypeOf<(Dependency?) -> Dependency?>() {}
    override fun invoke(dependency: Dependency?): Dependency? {
        if (dependency != null) {
            imports.add("${dependency.group}:${dependency.name}")
        }
        return dependency
    }

    override fun getPublicType(): TypeOf<*> {
        return type
    }
}
