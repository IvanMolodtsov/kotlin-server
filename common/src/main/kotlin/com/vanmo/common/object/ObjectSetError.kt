package com.vanmo.common.`object`

class ObjectSetError(override val message: String, override val cause: Throwable? = null) : Error(message, cause)
