package com.vanmo.test

import com.vanmo.common.annotations.DTO
import com.vanmo.common.annotations.Transform
import com.vanmo.common.annotations.Validate

@DTO
interface TestInterface {
    @Transform("toInt",)
    @Validate("positive")
    var A: Int
    var B: String
    var C: Array<Int>
}
