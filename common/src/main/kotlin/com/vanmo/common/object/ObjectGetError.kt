package com.vanmo.common.`object`

class ObjectGetError(override val message: String, override val cause: Throwable? = null) : Error(message, cause)
