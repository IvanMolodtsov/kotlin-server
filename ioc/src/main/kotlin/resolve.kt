
inline fun <reified T> resolve(key: String, vararg arguments: Any): T {
    return T::class as T
}
