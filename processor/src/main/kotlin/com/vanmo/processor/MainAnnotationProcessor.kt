package com.vanmo.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import java.io.OutputStream

class MainAnnotationProcessor(annotation: String, resolver: Resolver) : AnnotationProcessor(annotation, resolver) {

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

    override fun visitor(file: OutputStream): KSVisitorVoid {
        return Visitor(file)
    }
}
