package com.vanmo.test

import com.vanmo.common.annotations.DataClass

@DataClass
interface TestInterface {
    var A: Int
    var B: String
    var C: Array<Int>
}
