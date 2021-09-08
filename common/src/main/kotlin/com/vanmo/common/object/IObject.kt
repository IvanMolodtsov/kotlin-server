package com.vanmo.common.`object`

import kotlin.jvm.Throws

interface IObject {
    @Throws(ObjectGetError::class)
    operator fun get(key: String, orElse: () -> Any = { throw ObjectGetError("key $key not found.") }): Any
}
