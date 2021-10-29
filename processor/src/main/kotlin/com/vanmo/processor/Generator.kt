package com.vanmo.processor

import com.google.auto.service.AutoService
import com.vanmo.common.command.Command
import com.vanmo.ioc.asDependency
import com.vanmo.processor.files.MainFile
import com.vanmo.processor.processors.IDependencyProcessor
import com.vanmo.processor.processors.IProcessor
import com.vanmo.resolve
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes(
    "com.vanmo.common.annotations.IDependency",
    "com.vanmo.common.annotations.ICommand",
    "com.vanmo.common.annotations.DataClass"
)
@SupportedOptions("kapt.kotlin.generated", "project.name", "project.group")
class Generator : AbstractProcessor() {

    lateinit var mainFile: MainFile

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        val dir = processingEnv?.options?.get("kapt.kotlin.generated")!!
        val group = processingEnv.options["project.group"]!!
        val name = processingEnv.options["project.name"]!!
        mainFile = MainFile(dir, "$group.generated", name)
        resolve<Command>("IoC.Register", "Files.Main", asDependency { mainFile })()
    }

    var processors: MutableList<IProcessor> = mutableListOf(IDependencyProcessor())

    override fun process(annotations: MutableSet<out TypeElement>?, env: RoundEnvironment): Boolean {

        processors.forEach {
            val result = it.process(env, processingEnv)
            if (!result) return result
        }
        return true
    }
}
