package com.vanmo.serialization

import com.vanmo.common.`object`.UObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable(with = ObjectSerializer::class)
class SerializableObject(val map: MutableMap<String, Any>) : UObject {

    override fun get(key: String, orElse: () -> Any): Any {
        return map[key]!!
    }

    override fun set(key: String, value: Any) {
        map[key] = Json.encodeToString(value)
    }

    override fun toString(): String {
        return map.toString()
    }
}
