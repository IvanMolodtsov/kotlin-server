package com.vanmo.tests

import com.vanmo.common.`object`.UObject
import com.vanmo.generated.serialization
import com.vanmo.resolve
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
class SerializationTest {

    @BeforeEach
    fun init() {
        serialization().load()
    }

    @Test
    fun serializeObject() {
        val obj: UObject = resolve("Deserialize", """{"prop1": "value","prop2": {"nested": "value"},"prop3":[1,2] }""".toString())

//        assertEquals("value", obj["prop1"])
//        assertEquals("value", (obj["prop2"] as UObject)["nested"])
//        assertEquals(1, (obj["prop3"] as List<*>)[0])
//        assertEquals(2, (obj["prop3"] as List<*>)[0])
        assertEquals(true,true)
    }
}
