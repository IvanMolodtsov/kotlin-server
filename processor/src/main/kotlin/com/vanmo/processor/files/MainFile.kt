package com.vanmo.processor.files

import com.squareup.kotlinpoet.*
import com.vanmo.common.plugins.IPlugin

class MainFile(packageName: String, private val className: String) : IFile(packageName, className) {

    private val importList = mutableListOf(
        ClassName("com.vanmo", "resolve"),
        ClassName("com.vanmo.common.command", "Command")
    )

    private var loadScript = ""

    fun addDependency(key: String, className: ClassName) {
        importList.add(className)
        loadScript += "resolve<Command>(\"IoC.Register\",\"$key\",${className.simpleName}())()\n"
    }

    override fun build(file: FileSpec.Builder): FileSpec {
        return file.apply {
            importList.forEach {
                addImport(it.packageName, it.simpleName)
            }
            addType(
                TypeSpec.classBuilder(className).apply {
                    addSuperinterface(IPlugin::class)
                    addFunction(
                        FunSpec.builder("load").apply {
                            addModifiers(KModifier.OVERRIDE)
                            addStatement(loadScript)
                        }.build()
                    )
                }.build()
            )
        }.build()
    }
}
