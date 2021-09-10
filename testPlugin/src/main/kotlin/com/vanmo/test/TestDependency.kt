package com.vanmo.test

import com.vanmo.ioc.Dependency

class TestDependency : Dependency {

    override fun invoke(arguments: Array<out Any>): Any {
        return arguments.size
    }
}
