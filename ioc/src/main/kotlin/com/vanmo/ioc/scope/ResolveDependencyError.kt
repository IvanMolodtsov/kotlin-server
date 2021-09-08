package com.vanmo.ioc.scope

class ResolveDependencyError(override val message: String, override val cause: Throwable? = null): Error(message, cause)