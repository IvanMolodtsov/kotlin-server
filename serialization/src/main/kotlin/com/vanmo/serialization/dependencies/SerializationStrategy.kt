package com.vanmo.serialization.dependencies

import com.vanmo.common.plugins.IDependency
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.resolve
import com.vanmo.serialization.SerializableObject
import kotlinx.serialization.json.*

@IDependency("Serialization")
class SerializationStrategy : Dependency {

    override fun invoke(p1: Array<out Any>): JsonElement {
        val element: Any = cast(p1[0])
        if (element is SerializableObject) return resolve("Object.serialize", element)
        if (element is ArrayList<*>) return resolve("Array.serialize", element)
        return JsonPrimitive(element.toString())
    }
}
