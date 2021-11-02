package com.vanmo.processor.processors

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.vanmo.common.annotations.DataClass
import com.vanmo.processor.files.WrapperFile
import com.vanmo.resolve

class DataClassProcessor : IProcessor(DataClass::class) {

    private inner class Visitor : KSVisitorVoid() {
        @OptIn(KotlinPoetKspPreview::class)
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {

            val name = classDeclaration.toClassName()
            val file: WrapperFile = resolve("Files.Wrapper", name)
            val annotation = classDeclaration.annotations.first { it.shortName.asString() == "DataClass" }
            classDeclaration.getAllProperties().forEach {
                file.addProperty(it.simpleName.asString(), it.type.toTypeName(), it.isMutable)
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
