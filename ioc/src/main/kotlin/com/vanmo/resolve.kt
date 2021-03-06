package com.vanmo

import com.vanmo.ioc.Container
import com.vanmo.ioc.ResolveDependencyError
import kotlin.jvm.Throws

@Throws(ResolveDependencyError::class)
inline fun <reified T> resolve(key: String, vararg arguments: Any): T {
    try {
        return Container.currentScope[key](arguments) as T
    } catch (ex: ResolveDependencyError) {
        throw ex
    } catch (ex: Throwable) {
        throw ResolveDependencyError("IoC dependency for $key thrown unexpected exception.", ex)
    }
}
