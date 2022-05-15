package com.vanmo.processor.files

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName
import com.vanmo.common.annotations.Transform
import com.vanmo.common.`object`.UObject

class WrapperFile(packageName: String, private val className: ClassName) : IFile(packageName, "${className.simpleName}Wrapper") {

    private val properties: MutableList<PropertySpec.Builder> = mutableListOf()

    private fun transform(ann: KSAnnotation?, field: String, type: TypeName): String {
        val objGet = "this.obj[\"$field\"]"
        return if (ann !== null) {
            val strategy = ann.arguments[0].value as String
            val optString = ann.arguments[1].value
            if (optString !== null) {
                var opt = "val opts: Map<String, String> = mapOf("
                (optString as String).apply {
                    split(' ').forEach {
                        val pair = it.split(':').toTypedArray()
                        println(pair)
                        opt += "\"${pair[0]}\" to \"${pair[1]}\","
                    }
                }
                opt += ") \n"
                opt + "var data: $type = resolve(\"Wrapper.transform\", \"${strategy}\", $type::class, $objGet, opts)\n"
            } else {
                "var data: $type = resolve(\"Wrapper.transform\", \"${strategy}\", $type::class, $objGet)\n"
            }
        } else {
            "var data: $type = resolve(\"Wrapper.transform\", \"basic\", $type::class, $objGet)\n"
        }
    }

    @OptIn(KotlinPoetKspPreview::class)
    fun addProperty(prop: KSPropertyDeclaration) {
        val name = prop.simpleName.asString()
        val typeName = prop.type.toTypeName()
        val annotations = prop.annotations
        val transform = annotations.find { it.shortName.asString() == Transform::class.simpleName.toString() }
        properties.add(
            PropertySpec.builder(name, typeName, KModifier.OVERRIDE).apply {
                getter(
                    FunSpec.getterBuilder().apply {
                        addCode(transform(transform, name, typeName))
                        addCode("return data")
                    }.build()
                )
                if (prop.isMutable) {
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
