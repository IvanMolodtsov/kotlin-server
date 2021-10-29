package com.vanmo.processor.processors

import com.vanmo.common.annotations.IDependency
import com.vanmo.processor.files.MainFile
import com.vanmo.resolve
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class IDependencyProcessor : IProcessor(IDependency::class.java) {

    override fun processAnnotation(symbols: Set<Element>, processingEnv: ProcessingEnvironment): Boolean {
        val file: MainFile = resolve("Files.Main")
        for (s in symbols) {
            if (s.kind !== ElementKind.CLASS) {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "only classes are allowed", s)
                return false
            }
            val clazz = s as TypeElement

            val className = clazz.simpleName.toString()
            val pName = processingEnv.elementUtils.getPackageOf(s).qualifiedName.toString()
            val ann = s.getAnnotation(IDependency::class.java)
            val key = ann.key
            file.addDependency(key, pName, className)
        }
        file.load()
        return true
    }
}
