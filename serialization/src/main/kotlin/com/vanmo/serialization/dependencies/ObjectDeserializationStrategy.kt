package com.vanmo.serialization.dependencies

import com.vanmo.common.`object`.UObject
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.ResolveDependencyError
import com.vanmo.ioc.cast
import com.vanmo.resolve
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class ObjectDeserializationStrategy : Dependency {
    override fun invoke(args: Array<out Any>): UObject {
        val element: JsonObject = cast(args[0]) {
            if (it is String) {
                Json.decodeFromString(JsonObject.serializer(), it)
            } else {
                throw ResolveDependencyError("Expected json string.")
            }
        }
        val result = HashMap<String, Any>()
        element.entries.forEach {
            result[it.key] = resolve("Any.deserialize", it.value)
        }
        return resolve("UObject serializable", result)
    }
}
