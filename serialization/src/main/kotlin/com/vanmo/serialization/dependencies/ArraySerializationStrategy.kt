package com.vanmo.serialization.dependencies

import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.resolve
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

class ArraySerializationStrategy : Dependency {
    override fun invoke(args: Array<out Any>): JsonArray {
        val arr: MutableList<Any> = cast(args[0])
        val list = mutableListOf<JsonElement>()
        arr.forEach {
            list.add(resolve("Any.serialize", it))
        }
        return JsonArray(list)
    }
}
