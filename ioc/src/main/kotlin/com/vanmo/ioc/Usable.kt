package com.vanmo.ioc

import java.io.Closeable

interface Usable : Closeable {
    fun <R> use(block: (Usable) -> R): R
}
