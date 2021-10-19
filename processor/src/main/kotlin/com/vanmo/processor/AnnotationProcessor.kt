package com.vanmo.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import java.io.OutputStream

abstract class AnnotationProcessor(private val annotation: String, private val resolver: Resolver) {
    abstract fun visitor(file: OutputStream): KSVisitorVoid

    operator fun OutputStream.plusAssign(str: String) {
        this.write("$str\n".toByteArray())
    }

    fun processAnnotation(file: OutputStream): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("com.vanmo.common.plugins.Main")
            .filterIsInstance<KSClassDeclaration>()
        if (!symbols.iterator().hasNext()) return emptyList()

        symbols.forEach {
            it.accept(visitor(file), Unit)
        }

        return symbols.filterNot { it.validate() }.toList()
    }
}
