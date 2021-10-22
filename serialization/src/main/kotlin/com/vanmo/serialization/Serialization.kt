package com.vanmo.serialization

import com.vanmo.common.command.Command
import com.vanmo.common.plugins.IPlugin
import com.vanmo.common.plugins.PluginLoadError
import com.vanmo.ioc.asDependency
import com.vanmo.resolve
import com.vanmo.serialization.dependencies.*
import kotlinx.serialization.json.Json

class Serialization : IPlugin {

    override fun load() {
        try {
            resolve<Command>("IoC.Register", "Object.deserialize", ObjectDeserializationStrategy())()
            resolve<Command>("IoC.Register", "Object.serialize", ObjectSerializationStrategy())()
            resolve<Command>("IoC.Register", "Array.deserialize", ArrayDeserializationStrategy())()
            resolve<Command>("IoC.Register", "Array.serialize", ArraySerializationStrategy())()
            resolve<Command>("IoC.Register", "Primitive.deserialize", PrimitiveDeserializationStrategy())()
            resolve<Command>("IoC.Register", "Any.deserialize", DeserializationStrategy())()
            resolve<Command>("IoC.Register", "Any.serialize", SerializationStrategy())()
            resolve<Command>(
                "IoC.Register", "Serialize",
                asDependency {
                    resolve<Any>("Any.serialize", it[0]).toString()
                }
            )()
            resolve<Command>(
                "IoC.Register", "Deserialize",
                asDependency {
                    val element = Json.parseToJsonElement(it[0] as String)
                    resolve("Any.deserialize", element)
                }
            )()
            resolve<Command>("IoC.Register", "UObject serializable", NewSerializableObject())()
        } catch (ex: Throwable) {
            throw PluginLoadError("Serialization module filed to load", ex)
        }
    }
}
