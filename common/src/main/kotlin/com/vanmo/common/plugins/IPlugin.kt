package com.vanmo.common.plugins

import kotlin.jvm.Throws

interface IPlugin {

    @Throws(PluginLoadError::class)
    fun load()
}
