package com.vanmo.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import java.io.OutputStream

class MainProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {
    var i = 0
    inner class Visitor(private val file: OutputStream) : KSVisitorVoid() {

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            file += "${classDeclaration.packageName.asString()}.${classDeclaration.simpleName.asString()}"
        }
    }

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("com.vanmo.common.plugins.Main")
            .filterIsInstance<KSClassDeclaration>()
        if (!symbols.iterator().hasNext()) return emptyList()

        val file: OutputStream = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "",
            fileName = "EntryPoint",
            extensionName = "txt"
        )

        symbols.forEach {
            i++
            if (i > 1) logger.error("Found more than 1 @Main class")
            it.accept(Visitor(file), Unit)
        }
        file.close()
        val unableToProcess = symbols.filterNot { it.validate() }.toList()
        return unableToProcess
    }
}
