package com.vanmo.test

import com.vanmo.common.annotations.IDependency
import com.vanmo.ioc.Dependency

@IDependency("Test Dependency")
class TestDependency : Dependency {

    override fun invoke(arguments: Array<out Any>): Any {
        return arguments.size
    }
}
