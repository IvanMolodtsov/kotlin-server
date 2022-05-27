package com.vanmo.processor.files

import com.google.devtools.ksp.processing.KSPLogger
import com.vanmo.common.command.Command
import com.vanmo.ioc.asDependency
import com.vanmo.processor.dependencies.DTOWrapperNew
import com.vanmo.resolve

fun Files.dependencies(mainName: String, logger: KSPLogger) {
    resolve<Command>("IoC.Register", "Files.Main", asDependency { this.file("Main") { MainFile(it, mainName) } })()
    resolve<Command>("IoC.Register", "Files.DTOWrapper", DTOWrapperNew(this))()
    resolve<Command>("IoC.Register", "Files.Logger", asDependency { logger })()
}
