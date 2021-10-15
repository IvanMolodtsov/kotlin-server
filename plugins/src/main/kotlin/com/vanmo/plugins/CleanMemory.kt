package com.vanmo.plugins

import com.vanmo.common.command.Command
import com.vanmo.ioc.Dependency

class CleanMemory : Dependency {

    override fun invoke(p1: Array<out Any>): Command {
        return {
            System.gc()
        }
    }
}
