package com.vanmo.processor.files

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.*
import com.vanmo.common.`object`.UObject

class DependencyWrapperFile(packageName: String, private val fn: KSFunctionDeclaration) : IFile(packageName, "${fn.simpleName}Wrapper") {
    override fun build(file: FileSpec.Builder): FileSpec {
        val params = fn.parameters
        val packageName = fn.packageName.toString()
        val name = fn.simpleName.toString()
        return file.apply {
            addImport("com.vanmo.ioc", "cast")
            addImport(packageName, name)
            addType(
                TypeSpec.classBuilder("${fn.simpleName}Wrapper").apply {
                    primaryConstructor(
                        FunSpec.constructorBuilder().apply {
                            addParameter("obj", UObject::class)
                        }.build()
                    )
                    addSuperinterface(ClassName("com.vanmo.ioc", "Dependency"))
                    addFunction(
                        FunSpec.builder("invoke").apply {
                            addModifiers(KModifier.OVERRIDE)
                            addParameter(
                                ParameterSpec.builder("args", Any::class, KModifier.VARARG).build()
                            )
                            params.forEachIndexed() { i, p ->
                                addCode("val ${p.name}: ${p.type} = cast(args[$i)\n")
                            }
                            returns(Any::class)
                        }.build()
                    )
                }.build()
            )
        }.build()
    }
}
