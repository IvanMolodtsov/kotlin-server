package com.vanmo.processor.files

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.vanmo.common.plugins.IPlugin

class MainFile(directory: String, packageName: String, className: String) : IFile(directory, packageName, className) {

    private val importList = mutableListOf(
        Pair("com.vanmo", "resolve"),
        Pair("com.vanmo.common.command", "Command")
    )

    private var loadScript = "\n"

    fun addDependency(key: String, packageName: String, className: String) {
        importList.add(Pair(packageName, className))
        loadScript += "resolve<Command>(\"IoC.Register\",\"$key\",$className())()\n"
    }

    override fun build(file: FileSpec.Builder): FileSpec {
        return file.apply {
            importList.forEach {
                val (pack, name) = it
                addImport(pack, name)
            }
            addType(
                TypeSpec.classBuilder(name).apply {
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
