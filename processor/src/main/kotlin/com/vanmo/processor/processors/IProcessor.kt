package com.vanmo.processor.processors

import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

abstract class IProcessor(private val annotation: Class<out Annotation>) {

    abstract fun processAnnotation(symbols: Set<Element>, processingEnv: ProcessingEnvironment): Boolean

    fun process(env: RoundEnvironment, processingEnv: ProcessingEnvironment): Boolean {
        val symbols = env.getElementsAnnotatedWith(annotation)
        if (symbols.isEmpty()) return true
        return processAnnotation(symbols, processingEnv)
    }
}
