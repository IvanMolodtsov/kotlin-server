package com.vanmo.processor.processors

import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.toClassName
import com.vanmo.common.annotations.IDependency
import com.vanmo.processor.files.MainFile
import com.vanmo.resolve

class IDependencyProcessor() : IProcessor(IDependency::class) {

    private inner class Visitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val file = resolve<MainFile>("Files.Main")
            val name = classDeclaration.toClassName()
            val annotation = classDeclaration.annotations.first { it.shortName.asString() == IDependency::class.simpleName }
            val key = annotation.arguments[0].value!! as String
            file.addDependency(key, name)
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val file = resolve<MainFile>("Files.Main")
            function
        }
    }

    override fun processAnnotation(symbols: Sequence<KSAnnotated>) {
        symbols.filter {
            it is KSDeclaration && it.validate()
        }.forEach {
            it.accept(Visitor(), Unit)
        }
    }
}
