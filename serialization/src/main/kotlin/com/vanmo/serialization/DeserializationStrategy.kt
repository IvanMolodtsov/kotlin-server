package com.vanmo.serialization

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import kotlinx.serialization.json.*

class DeserializationStrategy : Dependency {

    override fun invoke(p1: Array<out Any>): Any {
        val element: JsonElement = cast(p1[0])
        if (element is JsonObject) return Json.decodeFromJsonElement(ObjectSerializer, element)
        if (element is JsonArray) return Json.decodeFromJsonElement(ArraySerializer, element)
        if (element is JsonPrimitive) return Json.decodeFromJsonElement(PrimitiveSerializer, element)
        throw ResolveDependencyError("Invalid JSON")
    }
}
