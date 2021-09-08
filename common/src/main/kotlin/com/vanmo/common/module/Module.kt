package com.vanmo.common.module

import kotlin.jvm.Throws

interface Module {

    @Throws(ModuleLoadError::class)
    fun load()
}
