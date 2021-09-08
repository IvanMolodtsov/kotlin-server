package com.vanmo.common.module

class ModuleLoadError(override val message: String, override val cause: Throwable? = null) : Error(message, cause)
