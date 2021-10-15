package com.vanmo.serialization.dependencies

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.resolve
import kotlinx.serialization.json.*

class DeserializationStrategy : Dependency {

    override fun invoke(p1: Array<out Any>): Any {
        val element: JsonElement = cast(p1[0])
        if (element is JsonObject) return resolve("Object.deserialize", element)
        if (element is JsonArray) return resolve("Array.deserialize", element)
        if (element is JsonPrimitive) return resolve("Primitive.deserialize", element)
        throw ResolveDependencyError("Invalid JSON")
    }
}
