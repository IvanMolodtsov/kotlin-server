package com.vanmo.processor.files

import com.squareup.kotlinpoet.*
import com.vanmo.common.`object`.UObject

class WrapperFile(packageName: String, private val className: String) : IFile(packageName, "${className}Wrapper") {

    lateinit var parent: TypeName

    private val properties: HashMap<String, PropertySpec.Builder> = hashMapOf()
    fun addAccessor(fname: String, type: TypeName) {
        val name = fname.drop(3)
        val prop = properties.getOrPut(name) {
            PropertySpec.builder(name, type, KModifier.OVERRIDE)
        }
        if (fname.startsWith("set")) {
            prop.mutable(true)
            prop.setter(
                FunSpec.setterBuilder().apply {
                    addParameter("value", type)
                    addCode("resolve<Unit>(\"set\", value)\n")
                }.build()
            )
        } else {
            prop.getter(
                FunSpec.getterBuilder().apply {
                    addCode("return resolve(\"get\")\n")
                }.build()
            )
        }
    }

    override fun build(file: FileSpec.Builder): FileSpec {
        return file.apply {
            addImport("com.vanmo", "resolve")
            addType(
                TypeSpec.classBuilder("${className}Wrapper").apply {
                    primaryConstructor(
                        FunSpec.constructorBuilder().apply {
                            addParameter("obj", UObject::class)
                        }.build()
                    )
                    addProperty(PropertySpec.builder("obj", UObject::class, KModifier.PRIVATE).initializer("obj").build())
                    addSuperinterface(parent)
                    properties.values.forEach {
                        addProperty(it.build())
                    }
                }.build()
            )
        }.build()
    }
}
