package com.vanmo.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.json.JsonElement

object ObjectSerializer : KSerializer<SerializableObject> {
    val serializer = MapSerializer(String.serializer(), JsonElement.serializer())
    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): SerializableObject {
        val res = serializer.deserialize(decoder)
        val res2 = HashMap<String, Any>()
        res.forEach {
            res2[it.key] = DeserializationStrategy()(arrayOf(it.value))
        }
        return SerializableObject(res2)
    }

    override fun serialize(encoder: Encoder, value: SerializableObject) {
        TODO("Not yet implemented")
    }
}
