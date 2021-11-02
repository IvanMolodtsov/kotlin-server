package com.vanmo.processor.files

import com.vanmo.common.command.Command
import com.vanmo.ioc.asDependency
import com.vanmo.processor.dependencies.WrapperNew
import com.vanmo.resolve

fun Files.deps(mainName: String) {
    resolve<Command>("IoC.Register", "Files.Main", asDependency { this.file("Main") { MainFile(it, mainName) } })()
    resolve<Command>("IoC.Register", "Files.Wrapper", WrapperNew(this))()
}
