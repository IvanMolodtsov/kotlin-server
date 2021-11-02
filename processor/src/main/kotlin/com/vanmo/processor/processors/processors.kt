package com.vanmo.processor.processors

fun processors(): List<IProcessor> {
    return listOf(
        IDependencyProcessor(),
        DataClassProcessor()
    )
}
