package com.vanmo.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import java.io.OutputStream
import java.util.*

class MainProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {
    var i = 0

    private val filename = options["project-name"] ?: "Entry_Point"
    private val group = options["project-group"] ?: "generated"
    private val file: OutputStream = codeGenerator.createNewFile(
        dependencies = Dependencies(false),
        packageName = group,
        fileName = filename.uppercase(Locale.getDefault())
    ).apply {
        this += "package $group"
    }

    private val processors: List<AnnotationProcessor> = listOf(
        MainAnnotationProcessor()
    )

    operator fun OutputStream.plusAssign(str: String) {
        this.write("$str\n".toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val result = mutableListOf<KSAnnotated>()

        processors.forEach {
            result += it.processAnnotation(file, resolver).toList()
        }
        file.close()
        return result
    }
}
