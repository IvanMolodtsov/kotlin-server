package com.vanmo.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.vanmo.processor.files.Files
import com.vanmo.processor.files.dependencies
import com.vanmo.processor.processors.IProcessor
import com.vanmo.processor.processors.processors

class Processor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    lateinit var dependencies: Dependencies
    private val files: Files
    private val processors: List<IProcessor>

    init {
        val group = options["project.group"]!!
        val name = options["project.name"]!!
        files = Files("$group.generated")
        files.dependencies(name, logger)
        processors = processors()
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray())

        for (p in processors) {
            p.process(resolver)
        }

        return emptyList()
    }

    override fun finish() {
        files.load(codeGenerator, dependencies)
    }
}
