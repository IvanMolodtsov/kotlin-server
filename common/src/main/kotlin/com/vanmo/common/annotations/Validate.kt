package com.vanmo.common.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
@Repeatable()
annotation class Validate(val strategy: String, val options: String = "")
