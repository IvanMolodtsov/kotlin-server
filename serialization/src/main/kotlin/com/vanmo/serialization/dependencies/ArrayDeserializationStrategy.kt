package com.vanmo.serialization.dependencies

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.resolve
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray

class ArrayDeserializationStrategy : Dependency {
    override fun invoke(args: Array<out Any>): MutableList<Any> {
        val element: JsonArray = cast(args[0]) {
            if (it is String) {
                Json.decodeFromString(JsonArray.serializer(), it)
            } else {
                throw ResolveDependencyError("Expected json string.")
            }
        }
        return element.map {
            resolve<Any>("Primitive.deserialize", it)
        }.toMutableList()
    }
}
