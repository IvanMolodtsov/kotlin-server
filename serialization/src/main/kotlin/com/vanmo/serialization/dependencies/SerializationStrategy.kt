package com.vanmo.serialization.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.ioc.Dependency
import com.vanmo.serialization.SObject
import kotlinx.serialization.json.*

@IDependency("Serialize")
class SerializationStrategy : Dependency {

    private fun objectStrategy(obj: SObject): JsonObject {
        val map = mutableMapOf<String, JsonElement>()
        obj.map.entries.forEach {
            map[it.key] = anyStrategy(it.value)
        }
        return JsonObject(map)
    }

    private fun arrayStrategy(iterable: Array<*>): JsonArray {
        val list = mutableListOf<JsonElement>()
        iterable.forEach {
            list.add(anyStrategy(it))
        }
        return JsonArray(list)
    }

    private fun listStrategy(iterable: Iterable<*>): JsonArray {
        val list = mutableListOf<JsonElement>()
        iterable.forEach {
            list.add(anyStrategy(it))
        }
        return JsonArray(list)
    }

    private fun primitiveStrategy(primitive: Any?): JsonPrimitive {
        return when (primitive) {
            is Number -> JsonPrimitive(primitive)
            is Boolean -> JsonPrimitive(primitive)
            is String -> JsonPrimitive(primitive)
            else -> {
                JsonNull
            }
        }
    }

    private fun anyStrategy(element: Any?): JsonElement {
        return when (element) {
            is SObject -> objectStrategy(element)
            is Array<*> -> arrayStrategy(element)
            is List<*> -> listStrategy(element)
            else -> {
                primitiveStrategy(element)
            }
        }
    }

    override fun invoke(args: Array<out Any>): String {
        return if (args.size == 1) {
            anyStrategy(args[0]).toString()
        } else {
            anyStrategy(args).toString()
        }
    }
}
