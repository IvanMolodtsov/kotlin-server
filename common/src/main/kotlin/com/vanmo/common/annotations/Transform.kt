package com.vanmo.common.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Transform(val strategy: String, vararg val options: String)
