package com.vanmo.processor.files

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName
import com.vanmo.common.annotations.Transform
import com.vanmo.common.annotations.Validate
import com.vanmo.common.`object`.UObject

class WrapperFile(packageName: String, private val className: ClassName) : IFile(packageName, "${className.simpleName}Wrapper") {

    private val properties: MutableList<PropertySpec.Builder> = mutableListOf()
    private val methods: MutableList<FunSpec.Builder> = mutableListOf()

    private fun validation(annotations: List<KSAnnotation>, field: String): String {
        var body = "val err: MutableList<ValidationError> = mutableListOf() \n"
        var optional = ""
        annotations.forEach { ann ->
            val strategy = ann.arguments[0].value as String
            val optString = ann.arguments[1].value as String?
            var opt = ""
            if (optString !== null && optString.isNotEmpty()) {
                opt = "mapOf<String, String>("
                optString.apply {
                    split(' ').forEach {
                        if (it.isNotEmpty()) {
                            val pair = it.split(':').toTypedArray()
                            opt += "\"${pair[0]}\" to \"${pair[1]}\","
                        }
                    }
                }
                opt += ")"
            }
            if (strategy === "optional") {
                optional = "if (resolve(\"validation.optional\", this.obj, \"$field\")) return; \n"
            } else {
                body += "resolve<ValidationError?>(\"$strategy\", data, $opt).also {\n    if (it !== null) { err.add(it) }\n}\n"
            }
        }

        if (optional !== "") {
            body = optional + body
        }

        body += "if (err.isNotEmpty()) {\n" +
            "throw ValidationError(\"$field field validation failed\", err)\n" +
            "}"
        return body
    }

    private fun transformFrom(ann: KSAnnotation?, type: TypeName): String {
        return if (ann !== null) {
            val strategy = ann.arguments[0].value as String
            val optString = ann.arguments[1].value as String?
            if (optString !== null && optString.isNotEmpty()) {
                var opt = "val opts: Map<String, String> = mapOf("
                optString.apply {
                    split(' ').forEach {
                        if (it.isNotEmpty()) {
                            val pair = it.split(':').toTypedArray()
                            opt += "\"${pair[0]}\" to \"${pair[1]}\","
                        }
                    }
                }
                opt += ") \n"
                opt + "var data: $type = resolve(\"transform.from\", \"${strategy}\", value, $type::class, this.obj, opts)\n"
            } else {
                "var data: $type = resolve(\"transform.from\", \"${strategy}\", value, $type::class, this.obj)\n"
            }
        } else {
            "var data: $type = resolve(\"transform.from\", \"basic\", value, $type::class, this.obj)\n"
        }
    }

    private fun transformTo(ann: KSAnnotation?, field: String, type: TypeName): String {
        return if (ann !== null) {
            val strategy = ann.arguments[0].value as String
            val optString = ann.arguments[1].value as String?
            if (optString !== null) {
                var opt = "val opts: Map<String, String> = mapOf("
                optString.apply {
                    split(' ').forEach {
                        if (it.isNotEmpty()) {
                            val pair = it.split(':').toTypedArray()
                            opt += "\"${pair[0]}\" to \"${pair[1]}\","
                        }
                    }
                }
                opt += ") \n"
                opt + "var data: $type = resolve(\"transform.to\", \"${strategy}\", \"$field\", $type::class, this.obj, opts)\n"
            } else {
                "var data: $type = resolve(\"transform.to\", \"${strategy}\", \"$field\", $type::class, this.obj)\n"
            }
        } else {
            "var data: $type = resolve(\"transform.to\", \"basic\", \"$field\", $type::class, this.obj)\n"
        }
    }

    @OptIn(KotlinPoetKspPreview::class)
    fun addProperty(prop: KSPropertyDeclaration) {
        val name = prop.simpleName.asString()
        val typeName = prop.type.toTypeName()
        val annotations = prop.annotations
        val transform = annotations.find {
            it.shortName.asString() == Transform::class.simpleName.toString()
        }
        val validations = annotations.filter {
            it.shortName.asString() == Validate::class.simpleName.toString()
        }.toList()

        if (validations.isNotEmpty()) {
            methods.add(
                FunSpec.builder("${name}FieldValidator",).apply {
                    addModifiers(KModifier.PRIVATE)
                    addParameter("data", typeName)
                    addCode(validation(validations, name))
                }
            )
        }

        properties.add(
            PropertySpec.builder(name, typeName, KModifier.OVERRIDE).apply {
                getter(
                    FunSpec.getterBuilder().apply {
                        addCode(transformTo(transform, name, typeName))
                        if (validations.isNotEmpty()) {
                            addCode("${name}FieldValidator(data) \n")
                        }
                        addCode("return data")
                    }.build()
                )
                if (prop.isMutable) {
                    mutable(true)
                    setter(
                        FunSpec.setterBuilder().apply {
                            addParameter("value", typeName)
                            addCode(transformFrom(transform, typeName))
                            if (validations.isNotEmpty()) {
                                addCode("${name}FieldValidator(data) \n")
                            }
                            addCode("this.obj[\"$name\"] = data")
                        }.build()
                    )
                }
            }
        )
    }

    override fun build(file: FileSpec.Builder): FileSpec {
        return file.apply {
            addImport("com.vanmo", "resolve")
            addImport("com.vanmo.common.validation", "ValidationError")
            addType(
                TypeSpec.classBuilder("${className.simpleName}Wrapper").apply {
                    primaryConstructor(
                        FunSpec.constructorBuilder().apply {
                            addParameter("obj", UObject::class)
                        }.build()
                    )
                    addProperty(PropertySpec.builder("obj", UObject::class, KModifier.PRIVATE).initializer("obj").build())
                    addSuperinterface(className)
                    methods.forEach {
                        addFunction(it.build())
                    }
                    properties.forEach {
                        addProperty(it.build())
                    }
                }.build()
            )
        }.build()
    }
}
