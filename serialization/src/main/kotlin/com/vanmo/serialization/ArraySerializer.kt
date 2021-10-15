package com.vanmo.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ArraySerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement

object ArraySerializer : KSerializer<MutableList<Any>> {
    @OptIn(ExperimentalSerializationApi::class)
    val serializer = ArraySerializer(JsonElement.serializer())

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): MutableList<Any> {
        val v = serializer.deserialize(decoder)
        val res = v.map {
            DeserializationStrategy()(arrayOf(it))
        }
        return res.toMutableList()
    }

    override fun serialize(encoder: Encoder, value: MutableList<Any>) {
        TODO("Not yet implemented")
    }
}
