package com.vanmo.common.`object`

import kotlin.jvm.Throws

interface UObject : IObject {

    @Throws(ObjectSetError::class)
    operator fun set(key: String, value: Any)
}
