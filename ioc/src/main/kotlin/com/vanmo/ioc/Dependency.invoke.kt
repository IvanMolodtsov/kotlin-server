package com.vanmo.ioc

operator fun Dependency.invoke(vararg arguments: Any): Any {
    return this.invoke(arguments)
}
