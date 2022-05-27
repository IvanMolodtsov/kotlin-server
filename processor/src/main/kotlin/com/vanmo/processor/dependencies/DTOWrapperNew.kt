package com.vanmo.processor.dependencies

import com.squareup.kotlinpoet.ClassName
import com.vanmo.ioc.Dependency
import com.vanmo.ioc.cast
import com.vanmo.processor.files.DTOWrapperFile
import com.vanmo.processor.files.Files
import com.vanmo.processor.files.IFile

class DTOWrapperNew(private val container: Files) : Dependency {

    override fun invoke(args: Array<out Any>): IFile {
        val className: ClassName = cast(args[0])
        return container.file("${className.simpleName}Wrapper") { DTOWrapperFile(it, className) }
    }
}
