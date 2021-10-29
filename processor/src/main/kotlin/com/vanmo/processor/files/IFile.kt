package com.vanmo.processor.files

import com.squareup.kotlinpoet.FileSpec
import java.io.File

abstract class IFile(private val directory: String, packageName: String, className: String) {

    private val file = FileSpec.builder(packageName, className)

    abstract fun build(file: FileSpec.Builder): FileSpec

    fun load() {
        val f = build(file)
        f.writeTo(File(directory))
    }
}
