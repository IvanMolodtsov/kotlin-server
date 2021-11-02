package com.vanmo.processor.files

import com.squareup.kotlinpoet.*
import com.vanmo.common.`object`.UObject

class WrapperFile(packageName: String, private val className: ClassName) : IFile(packageName, "${className.simpleName}Wrapper") {

    private val properties: MutableList<PropertySpec.Builder> = mutableListOf()

    fun addProperty(name: String, typeName: TypeName, mutable: Boolean) {
        properties.add(
            PropertySpec.builder(name, typeName, KModifier.OVERRIDE).apply {
                getter(
                    FunSpec.getterBuilder().apply {
                        addCode("return resolve(\"get\", \"${className.canonicalName}\", \"$name\", $typeName::class)")
                    }.build()
                )
                if (mutable) {
                    mutable(true)
                    setter(
                        FunSpec.setterBuilder().apply {
                            addParameter("v", typeName)
                            addCode("println(\"set\")")
                        }.build()
                    )
                }
            }
        )
    }

    override fun build(file: FileSpec.Builder): FileSpec {
        return file.apply {
            addImport("com.vanmo", "resolve")
            addType(
                TypeSpec.classBuilder("${className.simpleName}Wrapper").apply {
                    primaryConstructor(
                        FunSpec.constructorBuilder().apply {
                            addParameter("obj", UObject::class)
                        }.build()
                    )
                    addProperty(PropertySpec.builder("obj", UObject::class, KModifier.PRIVATE).initializer("obj").build())
                    addSuperinterface(className)
                    properties.forEach {
                        addProperty(it.build())
                    }
                }.build()
            )
        }.build()
    }
}
