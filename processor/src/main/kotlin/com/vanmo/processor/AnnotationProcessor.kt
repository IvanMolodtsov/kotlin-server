package com.vanmo.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import java.io.OutputStream

abstract class AnnotationProcessor(private val annotation: String) {
    abstract fun process(file: OutputStream, symbols: Sequence<KSAnnotated>)

    operator fun OutputStream.plusAssign(str: String) {
        this.write("$str\n".toByteArray())
    }

    fun processAnnotation(file: OutputStream, resolver: Resolver): Sequence<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(annotation)
        val ret = symbols.filter { !it.validate() }
//        if (!symbols.iterator().hasNext()) return emptyList()

        process(file, symbols)

        return ret
    }
}
