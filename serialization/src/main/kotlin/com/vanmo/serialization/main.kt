package com.vanmo.serialization

import com.vanmo.common.`object`.UObject
import com.vanmo.resolve
import kotlinx.serialization.*

@Serializable()
data class j(val group: String, val name: String, val version: String)

fun main() {
    Serialization().load()
//    val a = Json.decodeFromString<Array<SerializableObject>>("[{\"group\":\"com.vanmo\",\"name\":\"common\",\"version\":\"1.0.0\"},{\"group\":\"com.vanmo\",\"name\":\"ioc\",\"version\":\"1.0.0\"}]")
    val a = resolve<UObject>("Deserialize", "{\"group\":\"com.vanmo\",\"name\":{\"common\": 2},\"version\":[\"10000000000.0\"]}")
    println(a["group"]::class)
    println(a["name"]::class)
    println((a["name"] as UObject)["common"]::class)
    println(a["version"]::class)
    println((a["version"] as List<Any>)[0]::class)
    println(a)
    println(resolve<String>("Serialize", a))
}
