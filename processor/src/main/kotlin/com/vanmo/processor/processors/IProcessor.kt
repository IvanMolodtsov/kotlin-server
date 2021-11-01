package com.vanmo.processor.processors

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

abstract class IProcessor(private val annotation: KClass<out Annotation>) {

    abstract fun processAnnotation(symbols: Sequence<KSAnnotated>)

    fun process(resolver: Resolver) {
        val symbols = resolver.getSymbolsWithAnnotation(annotation.jvmName)
        if (!symbols.iterator().hasNext()) return
        processAnnotation(symbols)
    }
}
