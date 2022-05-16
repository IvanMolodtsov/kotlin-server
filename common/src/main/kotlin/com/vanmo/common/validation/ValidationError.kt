package com.vanmo.common.validation

class ValidationError(
    private val msg: String,
    val errors: Collection<ValidationError> = listOf()
) : Error(msg)
