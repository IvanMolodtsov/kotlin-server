package com.vanmo.plugins.dependencies

import com.vanmo.common.annotations.IDependency
import com.vanmo.common.command.Command
import com.vanmo.ioc.Dependency

@IDependency("Memory.clean")
class CleanMemory : Dependency {

    override fun invoke(p1: Array<out Any>): Command {
        return {
            System.gc()
        }
    }
}
