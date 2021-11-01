package com.vanmo.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.vanmo.processor.files.MainFile
import com.vanmo.processor.processors.IDependencyProcessor

class Processor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    lateinit var dependencies: Dependencies
    val mainFile: MainFile

    init {
        val group = options["project.group"]!!
        val name = options["project.name"]!!
        mainFile = MainFile("$group.generated", name)
//        resolve<Command>("IoC.Register", "Files.Main", mainFile)
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray())
        val p = IDependencyProcessor(mainFile)
        p.process(resolver)
        return emptyList()
    }

    override fun finish() {
        mainFile.load(codeGenerator, dependencies)
    }
}
