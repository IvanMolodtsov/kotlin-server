package com.vanmo.processor.processors

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.vanmo.common.annotations.DTO
import com.vanmo.processor.files.DTOWrapperFile
import com.vanmo.resolve

class DataClassProcessor : IProcessor(DTO::class) {

    private inner class Visitor : KSVisitorVoid() {
        @OptIn(KotlinPoetKspPreview::class)
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {

            val name = classDeclaration.toClassName()
            val file: DTOWrapperFile = resolve("Files.DTOWrapper", name)
            val annotation = classDeclaration.annotations.first { it.shortName.asString() == DTO::class.simpleName }
            classDeclaration.getAllProperties().forEach {
                file.addProperty(it)
            }
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
