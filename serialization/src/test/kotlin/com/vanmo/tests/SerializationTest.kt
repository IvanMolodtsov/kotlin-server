package com.vanmo.tests

import com.vanmo.common.`object`.UObject
import com.vanmo.generated.serialization
import com.vanmo.resolve
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SerializationTest {
    @BeforeEach
    fun init() {
        serialization().load()
    }

    @Test
    fun serializeObject() {
        val obj: UObject = resolve("SObject.new")
        val nested: UObject = resolve("SObject.new")
        nested["key"] = "value"
        obj["prop1"] = arrayOf(1, 2, 3)
        obj["prop2"] = nested
        obj["prop3"] = 2.5f

        val json: String = resolve("Serialize", obj)
        Assertions.assertEquals("""{"prop2":{"key":"value"},"prop1":[1,2,3],"prop3":2.5}""", json)
    }

    @Test
    fun serializeArray() {
        val json: String = resolve("Serialize", 1, 2, 3)
        Assertions.assertEquals("""[1,2,3]""", json)
    }
}
