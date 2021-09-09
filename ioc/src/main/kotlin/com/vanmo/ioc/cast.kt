package com.vanmo.ioc

import kotlin.jvm.Throws

@Throws(ResolveDependencyError::class)
inline fun <reified T> cast(argument: Any): T {
    if (argument is T) {
        return argument
    } else {
        throw ResolveDependencyError("Invalid argument type. Expected ${T::class}; Received ${argument::class}")
    }
}
