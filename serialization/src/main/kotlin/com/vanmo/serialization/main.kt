package com.vanmo.serialization

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable()
data class j(val group: String, val name: String, val version: String)

fun main() {
//    val a = Json.decodeFromString<Array<SerializableObject>>("[{\"group\":\"com.vanmo\",\"name\":\"common\",\"version\":\"1.0.0\"},{\"group\":\"com.vanmo\",\"name\":\"ioc\",\"version\":\"1.0.0\"}]")
    val a = Json.decodeFromString<SerializableObject>("{\"group\":\"com.vanmo\",\"name\":{\"common\": 2},\"version\":[\"1.0.0\"]}")
    println(a)
    println(Json.encodeToString(a))
}
