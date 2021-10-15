package com.vanmo.serialization.dependencies

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.resolve
import com.vanmo.serialization.SerializableObject
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

class ObjectSerializationStrategy : Dependency {
    override fun invoke(args: Array<out Any>): JsonObject {

        val obj: SerializableObject = cast(args[0])
        val map = mutableMapOf<String, JsonElement>()
        obj.map.entries.forEach {
            map[it.key] = resolve("Any.serialize", it.value)
        }
        return JsonObject(map)
    }
}
