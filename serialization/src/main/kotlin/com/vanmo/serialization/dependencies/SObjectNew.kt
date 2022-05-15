package com.vanmo.serialization.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.common.`object`.UObject
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.serialization.SObject

@IDependency("SObject.new")
class SObjectNew : Dependency {

    override fun invoke(args: Array<out Any>): UObject {
        val map: MutableMap<String, Any> = if (args.size == 1) {
            cast(args[0])
        } else {
            HashMap()
        }
        return SObject(map)
    }
}
