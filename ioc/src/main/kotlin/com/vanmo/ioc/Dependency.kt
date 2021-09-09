package com.vanmo.ioc

typealias Dependency = (Array<out Any>) -> Any

fun dependencyOf(fn: (Array<out Any>) -> Any): Dependency {
    return fn
}
