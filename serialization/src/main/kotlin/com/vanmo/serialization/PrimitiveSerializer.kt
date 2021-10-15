package com.vanmo.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonPrimitive

object PrimitiveSerializer : KSerializer<Any> {
    private val serializer = JsonPrimitive.serializer()

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): Any {
        val value = serializer.deserialize(decoder)
        return value.content
    }

    override fun serialize(encoder: Encoder, value: Any) {
        TODO("Not yet implemented")
    }
}
