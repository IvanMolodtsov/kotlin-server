package com.vanmo.ioc

class ResolveDependencyError(override val message: String, override val cause: Throwable? = null) : Error(message, cause)
