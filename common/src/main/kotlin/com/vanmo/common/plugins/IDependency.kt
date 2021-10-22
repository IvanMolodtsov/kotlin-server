package com.vanmo.common.plugins

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class IDependency(val key: String)
