package com.vanmo.testPlugin

import com.vanmo.common.annotations.DTO
import com.vanmo.common.annotations.Transform
import com.vanmo.common.annotations.Validate

@DTO
interface TestInterface {
    @Transform("toInt", "Arg1:val")
    @Validate("positive", "Arg1:val", "Arg2:val")
    var A: Int
    var B: String
    var C: Array<Int>
    var D: HashMap<String, Any>
}
