package br.com.anivicio.shared.util.serializers.encoding

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

/**
 * KJson polymorphic encoder
 *
 * This class is responsible for serialize a polymorphic class.
 * Attention: This class should only be used for serialize.
 *
 * @param T Type
 * @property baseClass Base class
 * @property subClasses Sub classes list
 * @property json Json
 * @constructor Create empty K json polymorphic encoder
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
abstract class KJsonPolymorphicEncoder<T : Any>(
    private val baseClass: KClass<T>,
    private val subClasses: List<KClass<out T>>,
    private val contentSerializer: KJsonContentPolymorphicSerializer<T>,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : KSerializer<T> {

    var simpleName: String? = null

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("KJsonPolymorphicSerializer<${baseClass.simpleName}>", PolymorphicKind.SEALED)

    override fun deserialize(decoder: Decoder): T {
        return contentSerializer.deserialize(decoder)
    }

    override fun serialize(encoder: Encoder, value: T) {
        val actualSerializer =
            encoder.serializersModule.getPolymorphic(baseClass, value)
                ?: value::class.serializerOrNull()
                ?: throw Error("Could not find serializer for ${value::class}")
        simpleName = actualSerializer.descriptor.serialName

        contentSerializer.serialize(encoder, value)
    }

    companion object {
        inline fun <reified T : Any> create(): KJsonPolymorphicEncoder<T> {
            val subClasses = T::class.sealedSubclasses
            val contentSerializer = KJsonContentPolymorphicSerializer.create<T>()
            return object : KJsonPolymorphicEncoder<T>(T::class, subClasses, contentSerializer) {}
        }

        inline fun <reified T : Any> create(json: Json): KJsonPolymorphicEncoder<T> {
            val subClasses = T::class.sealedSubclasses
            val contentSerializer = KJsonContentPolymorphicSerializer.create<T>()
            return object : KJsonPolymorphicEncoder<T>(T::class, subClasses, contentSerializer, json) {}
        }
    }
}


abstract class KJsonContentPolymorphicSerializer<T : Any>(
    private val baseClass: KClass<T>,
    private val subClassFields: Map<KClass<out T>, List<String>>,
    private val useBaseClass: Boolean
) : JsonContentPolymorphicSerializer<T>(baseClass) {

    var simpleName: String? = null

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out T> {
        if (useBaseClass) {
            return baseClass.serializer()
        }
        return subClassFields.entries.map { (kClass, fields) ->
            val jsonObject = element.jsonObject
            if (fields.all { jsonObject.containsKey(it) }) {
                val serializer = kClass.serializerOrNull() ?: throw Error("Could not find serializer for ${kClass}")
                simpleName = serializer.descriptor.serialName
                return@map serializer
            } else {
                return@map null
            }
        }.filterNotNull().firstOrNull() ?: throw Error("Could not find serializer for ${element}")
    }

    companion object {
        inline fun <reified T : Any> create(): KJsonContentPolymorphicSerializer<T> {
            val subClassFields =
                T::class.sealedSubclasses.associateWith { it.declaredMemberProperties.map { d -> d.name } }
            return object :
                KJsonContentPolymorphicSerializer<T>(T::class, subClassFields, subClassFields.isEmpty()) {}
        }
    }
}

open class WrapperContentSerializer<T : Any>(
    private val contentSerializer: KJsonContentPolymorphicSerializer<T>
) : KSerializer<T> {

    override val descriptor: SerialDescriptor = contentSerializer.descriptor
    override fun deserialize(decoder: Decoder): T {
        return contentSerializer.deserialize(decoder)
    }

    override fun serialize(encoder: Encoder, value: T) {
        contentSerializer.serialize(encoder, value)
    }
}
