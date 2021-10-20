package com.vanmo.processor

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import java.io.OutputStream

class MainAnnotationProcessor : AnnotationProcessor("com.vanmo.common.plugins.Main") {

    inner class Visitor(private val file: OutputStream) : KSVisitorVoid() {

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            file += "import com.vanmo.common.plugins.IPlugin"
            file += "import ${classDeclaration.packageName.asString()}.${classDeclaration.simpleName.asString()}"

            file += "class PluginLoader : IPlugin {"
            file += "override fun load() {"
            file += "${classDeclaration.simpleName.asString()}().load()"
            file += "}}"
        }
    }

    override fun process(file: OutputStream, symbols: Sequence<KSAnnotated>) {
        symbols
            .filter { it is KSClassDeclaration && it.validate() }
            .map { it.accept(Visitor(file), Unit) }
    }
}
