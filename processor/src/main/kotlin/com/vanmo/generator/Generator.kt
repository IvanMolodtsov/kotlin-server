package com.vanmo.generator

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.vanmo.common.plugins.IDependency
import com.vanmo.common.plugins.IPlugin
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("com.vanmo.common.plugins.IDependency")
@SupportedOptions("kapt.kotlin.generated")
class Generator : AbstractProcessor() {

    private var loadScript = "\n"
    private val importList = mutableListOf(
        Pair("com.vanmo", "resolve"),
        Pair("com.vanmo.common.command", "Command")
    )

    override fun process(annotations: MutableSet<out TypeElement>?, env: RoundEnvironment): Boolean {
        val symbols = env.getElementsAnnotatedWith(IDependency::class.java)
        val dir = processingEnv.options["kapt.kotlin.generated"]
        val group = processingEnv.options["project.group"]!!
        val name = processingEnv.options["project.name"]!!

        for (s in symbols) {
            if (s.kind !== ElementKind.CLASS) {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "only classes are allowed", s)
                return false
            }
            val clazz = s as TypeElement
            val className = clazz.simpleName.toString()
            val pName = processingEnv.elementUtils.getPackageOf(s).qualifiedName.toString()
            importList.add(Pair(pName, className))
            val ann = s.getAnnotation(IDependency::class.java)
            val key = ann.key
            loadScript += "resolve<Command>(\"IoC.Register\",\"$key\", $className())()\n"
        }

        val file = FileSpec.builder("$group.generated", name).apply {
            importList.forEach {
                addImport(it.first, it.second)
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
        file.writeTo(File(dir))
        return true
    }
}
