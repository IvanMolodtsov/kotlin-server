package com.vanmo.tests

import com.vanmo.common.command.Command
import com.vanmo.common.`object`.UObject
import com.vanmo.resolve
import com.vanmo.serialization.dependencies.DeserializationStrategy
import com.vanmo.serialization.dependencies.SObjectNew
import com.vanmo.serialization.dependencies.SerializationStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeserializationTest {

    @BeforeEach
    fun init() {
        resolve<Command>("IoC.Register", "Deserialize", DeserializationStrategy())()
        resolve<Command>("IoC.Register", "SObject.new", SObjectNew())()
        resolve<Command>("IoC.Register", "Serialize", SerializationStrategy())()
    }

    @Test
    fun deserializeObject() {
        val obj: UObject = resolve("Deserialize", """{"prop1": "value","prop2": {"nested": "value"},"prop3":[1,2] }""")
        assertEquals("value", obj["prop1"])
        assertEquals("value", (obj["prop2"] as UObject)["nested"])
        assertEquals(1, (obj["prop3"] as List<*>)[0])
        assertEquals(2, (obj["prop3"] as List<*>)[1])
    }

    @Test
    fun deserializeArray() {
        val list: List<Int> = resolve("Deserialize", """[1,2,3]""")
        assertEquals(1, list[0])
        assertEquals(2, list[1])
        assertEquals(3, list[2])
    }
}
