package br.com.caju.kafkacommons.extra.kotlinx.encoding

import br.com.anivicio.shared.util.serializers.UUIDSerializer
import br.com.anivicio.shared.util.serializers.checkIsStandardType
import br.com.anivicio.shared.util.serializers.encoding.KJsonContentPolymorphicSerializer
import br.com.anivicio.shared.util.serializers.encoding.WrapperContentSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import java.lang.reflect.Type
import java.util.*

/**
 * Is polymorphic
 *
 * Check if a descriptor is polymorphic
 *
 * @return Boolean
 */
@OptIn(ExperimentalSerializationApi::class)
fun SerialDescriptor.isPolymorphic(): Boolean {
    return this.kind == PolymorphicKind.SEALED || this.kind == PolymorphicKind.OPEN
}

/**
 * EncodeAs
 *
 * This function is responsible for encoding a value to a given type
 * It's a wrapper for kotlinx.serialization.json.Json.decodeFromJsonElement
 * And a more things that keep compatibility with encode as circe mode.
 *
 * @param T Type
 * @param value Value
 * @return String
 */
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T : Any> Json.encodeAs(value: String): T = run {
    if (!checkIsStandardType(T::class.java)) {
        val descriptor = serializer<T>().descriptor
        if (descriptor.isPolymorphic()) {
            val subClasses = T::class.sealedSubclasses
            val element = Json.decodeFromString(JsonElement.serializer(), value)
            val kind = element.jsonObject.keys.first()
            val subClass = subClasses.find { it.simpleName == kind }
            requireNotNull(subClass) { "Could not find subclass for $kind" }
            val elementUnwrapped = element.jsonObject[kind]
            val type: Type = subClass.java
            val kSerializer = serializer(type)
            if (elementUnwrapped != null) {
                return this.decodeFromJsonElement(kSerializer, elementUnwrapped) as T
            }
        }
        this.decodeFromString(
            serializer<T>(),
            value,
        )
    } else {
        when (T::class) {
            String::class -> value as T
            Int::class -> value.toInt() as T
            Long::class -> value.toLong() as T
            Double::class -> value.toDouble() as T
            Float::class -> value.toFloat() as T
            Boolean::class -> value.toBoolean() as T
            else -> throw IllegalArgumentException("Type not supported")
        }
    }
}

/**
 * WrapEncodeAs
 *
 * This function is responsible for wrapping the encodeAs function
 *
 * @param T Type
 * @return (String) -> T
 */
inline fun <reified T : Any> Json.wrapEncodeAs(): (String) -> T = run {
    return { value: String ->
        run {
            this.encodeAs<T>(value)
        }
    }
}


object SponsorDataSerializer : WrapperContentSerializer<SponsorData>(KJsonContentPolymorphicSerializer.create())

@Serializable
sealed interface SponsorData

@Serializable
data class CompanySponsorData(
    val name: String,
    val cnpj: String
) : SponsorData

@Serializable
data class IndividualSponsorData(
    val name: String,
    val cpf: String
) : SponsorData

@Serializable
data class UserData(
    val name: String
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = CreateSponsorRequest::class)
object CreateSponsorRequestSerializer :
    WrapperContentSerializer<CreateSponsorRequest>(KJsonContentPolymorphicSerializer.create())

@Serializable
sealed interface CreateSponsorRequest

@Serializable
data class SponsorWithExistingUser(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = SponsorDataSerializer::class)
    val sponsorData: SponsorData
) : CreateSponsorRequest

@Serializable
data class SponsorWithNewUser(
    @Serializable(with = SponsorDataSerializer::class)
    val sponsorData: SponsorData,
    val userData: UserData
) : CreateSponsorRequest


fun main() {
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    val sponsorRequest = SponsorWithNewUser(
        sponsorData = CompanySponsorData(
            name = "Caju",
            cnpj = "123456789"
        ),
        userData = UserData(
            name = "Caju"
        )
    )


    val sponsorRequestInString = """
        {
            "sponsorData": {
                "type": "CompanySponsorData",
                "name": "Caju",
                "cnpj": "123456789"
            },
            "userData": {
                "name": "Caju"
            }
        }
    """.trimIndent()

    val sponsorRequestForExistingUser = """ 
        {
            "userId": "a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a0a0",
            "sponsorData": {
                "type": "CompanySponsorData",
                "name": "Caju",
                "cnpj": "123456789"
            }
        }
    """.trimIndent()


    println(json.decodeFromString(CreateSponsorRequestSerializer, sponsorRequestForExistingUser))
    println(json.decodeFromString(CreateSponsorRequestSerializer, sponsorRequestInString))
//    println(json.decodeAs<CreateSponsorRequest>(sponsorRequest))
}
