package br.com.anivicio.shared.util.serializers.decoding

import br.com.anivicio.shared.util.serializers.checkIsStandardType
import br.com.anivicio.shared.util.serializers.encoding.KJsonPolymorphicEncoder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer

/**
 * Is polymorphic
 *
 * Check if a descriptor is polymorphic
 *
 * @receiver SerialDescriptor
 * @return Boolean
 */
@OptIn(ExperimentalSerializationApi::class)
fun SerialDescriptor.isPolymorphic(): Boolean {
    return this.kind == PolymorphicKind.SEALED || this.kind == PolymorphicKind.OPEN
}

/**
 * DecodeAs
 *
 * This function is responsible for decoding a value to a string
 * It's a wrapper for kotlinx.serialization.json.Json.encodeToString
 * That also checks if the type is polymorphic and if it is, it uses the KJsonPolymorphicEncoder
 * Keep compatibility with encode as circe mode.
 *
 * @param T Type
 * @param value Value
 * @param useFullName Use complete name for polymorphic
 * @return String
 */
inline fun <reified T : Any> Json.decodeAs(value: T, useFullName: Boolean = false): String = run {
    if (!checkIsStandardType(T::class.java)) {
        val descriptor = serializer<T>().descriptor
        if (descriptor.isPolymorphic()) {
            val serializer = KJsonPolymorphicEncoder.create<T>()
            val element = Json.encodeToJsonElement(serializer, value)
            requireNotNull(serializer.simpleName) { "Could not find simple name for serializer" }
            val name = if (useFullName) serializer.simpleName!! else serializer.simpleName!!.split(".").last()
            val jsonElement = JsonObject(mapOf(name to element))
            return jsonElement.toString()
        }
        this.encodeToString(
            serializer<T>(),
            value,
        )
    } else {
        when (T::class) {
            String::class -> value as String
            Int::class -> value.toString()
            Long::class -> value.toString()
            Double::class -> value.toString() as String
            Float::class -> value.toString() as String
            Boolean::class -> value.toString() as String
            else -> throw IllegalArgumentException("Type not supported")
        }
    }
}

/**
 * WrapDecodeAs
 *
 * This function is responsible for wrapping the decodeAs function
 *
 * @param T Type
 * @param useFullName Use complete name for polymorphic
 * @return (T) -> String
 */
inline fun <reified T : Any> Json.wrapDecodeAs(useFullName: Boolean = false): (T) -> String = run {
    return { value: T ->
        run {
            decodeAs(value, useFullName)
        }
    }
}