package com.vanmo.serialization.dependencies

import com.vanmo.common.`object`.UObject
import com.vanmo.common.plugins.IDependency
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.serialization.SerializableObject

@IDependency("")
class NewSerializableObject : Dependency {

    override fun invoke(args: Array<out Any>): UObject {
        val map: MutableMap<String, Any> = if (args.size == 1) {
            cast(args[0])
        } else {
            HashMap()
        }
        return SerializableObject(map)
    }
}
