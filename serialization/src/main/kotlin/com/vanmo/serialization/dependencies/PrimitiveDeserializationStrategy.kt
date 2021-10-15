package com.vanmo.serialization.dependencies

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*

class PrimitiveDeserializationStrategy : Dependency {

    override fun invoke(args: Array<out Any>): Any {
        val element: JsonPrimitive = cast(args[0]) {
            if (it is String) {
                Json.decodeFromString(JsonPrimitive.serializer(), it)
            } else {
                throw ResolveDependencyError("Expected json string.")
            }
        }
        if (element.intOrNull !== null) {
            return Json.decodeFromJsonElement(Int.serializer(), element)
        }
        if (element.longOrNull !== null) {
            return Json.decodeFromJsonElement(Long.serializer(), element)
        }
        if (element.booleanOrNull !== null) {
            return Json.decodeFromJsonElement(Boolean.serializer(), element)
        }
        if (element.floatOrNull !== null) {
            return Json.decodeFromJsonElement(Float.serializer(), element)
        }
        if (element.doubleOrNull !== null) {
            return Json.decodeFromJsonElement(Double.serializer(), element)
        }
        return Json.decodeFromJsonElement(String.serializer(), element)
    }
}
