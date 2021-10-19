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
            file += "package com.vanmo.generated"
            file += "import com.vanmo.common.plugins.IPlugin"
            file += "import ${classDeclaration.packageName.asString()}.${classDeclaration.simpleName.asString()}"

            file += "class PluginLoader : IPlugin {"
            file += "override fun load() {"
            file += "${classDeclaration.simpleName.asString()}().load()"
            file += "}}"
        }
    }

    operator fun OutputStream.plusAssign(str: String) {
        this.write("$str\n".toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val filename = options["project-name"] ?: "Entry_Point"
        val group = options["project-group"] ?: "generated"
        val symbols = resolver.getSymbolsWithAnnotation("com.vanmo.common.plugins.Main")
            .filterIsInstance<KSClassDeclaration>()
        if (!symbols.iterator().hasNext()) return emptyList()

        val file: OutputStream = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = group,
            fileName = filename
        )

        symbols.forEach {
            i++
            if (i > 1) logger.error("Found more than 1 @Main class")
            it.accept(Visitor(file), Unit)
        }
        file.close()
        return symbols.filterNot { it.validate() }.toList()
    }
}
