package com.vanmo.serialization

import com.vanmo.common.`object`.UObject

class SObject(internal val map: MutableMap<String, Any>) : UObject {

    override fun get(key: String, orElse: () -> Any): Any {
        return map.getOrElse(key, orElse)
    }

    override fun set(key: String, value: Any) {
        map[key] = value
    }

    override fun toString(): String {
        return map.toString()
    }
}
