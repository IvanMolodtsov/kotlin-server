package com.vanmo.test

import com.vanmo.common.annotations.DTO
import com.vanmo.common.annotations.Transform

@DTO
interface TestInterface {
    @Transform("toInt", "unsigned:true")
    var A: Int
    var B: String
    var C: Array<Int>
}
