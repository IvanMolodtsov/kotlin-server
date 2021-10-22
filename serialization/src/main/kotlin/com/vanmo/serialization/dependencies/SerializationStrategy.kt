package com.vanmo.serialization.dependencies

import com.vanmo.common.plugins.IDependency
import com.vanmo.ioc.Dependency
import com.vanmo.serialization.SObject
import kotlinx.serialization.json.*
import java.io.Serializable

@IDependency("Serialize")
class SerializationStrategy : Dependency {

    private fun objectStrategy(obj: SObject): JsonObject {
        val map = mutableMapOf<String, JsonElement>()
        obj.map.entries.forEach {
            map[it.key] = anyStrategy(it.value)
        }
        return JsonObject(map)
    }

    private fun arrayStrategy(iterable: Iterable<*>): JsonArray {
        val list = mutableListOf<JsonElement>()
        iterable.forEach {
            list.add(anyStrategy(it))
        }
        return JsonArray(list)
    }

    private fun primitiveStrategy(primitive: Any): JsonPrimitive {
        return JsonPrimitive(primitive.toString())
    }

    private fun anyStrategy(element: Any?): JsonElement {
        return when (element) {
            is SObject -> objectStrategy(element)
            is Iterable<*> -> arrayStrategy(element)
            is Serializable -> primitiveStrategy(element)
            else -> {
                return JsonNull
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
