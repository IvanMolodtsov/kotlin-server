package com.vanmo.serialization.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.resolve
import com.vanmo.serialization.SObject
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*

@IDependency("Deserialize")
class DeserializationStrategy : Dependency {

    private fun objectStrategy(obj: JsonObject): SObject {
        val result = HashMap<String, Any>()
        obj.entries.forEach {
            result[it.key] = anyStrategy(it.value)
        }
        return resolve("SObject.new", result)
    }

    private fun arrayStrategy(element: JsonArray): Iterable<Any> {
        return element.map {
            anyStrategy(it)
        }.toMutableList()
    }

    private fun primitiveStrategy(element: JsonPrimitive): Any {
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

    private fun anyStrategy(element: JsonElement): Any {
        return when (element) {
            is JsonObject -> objectStrategy(element)
            is JsonArray -> arrayStrategy(element)
            is JsonPrimitive -> primitiveStrategy(element)
            is JsonNull -> "null"
        }
    }

    override fun invoke(args: Array<out Any>): Any {
        val jsonString: String = cast(args[0])
        val element = Json.parseToJsonElement(jsonString)
        return anyStrategy(element)
    }
}
