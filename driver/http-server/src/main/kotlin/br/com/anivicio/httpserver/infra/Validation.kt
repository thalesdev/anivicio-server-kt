package br.com.anivicio.httpserver.infra

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import java.util.Locale

@Serializable
data class ValidationError(
    val field: String,
    val message: String
)


@Serializable
data class ValidationResult(
    @Transient
    val isValid: Boolean = false,
    val errors: List<ValidationError>
)

interface Validation {

    fun valid()

    fun checkIsValid(): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        val isValid = try {
            valid()
            true
        } catch (e: ConstraintViolationException) {
            e.constraintViolations
                .mapToMessage("messages", Locale.forLanguageTag("pt-BR"))
                .forEach {
                errors.add(ValidationError(it.property, it.message))
            }
            false
        }
        return ValidationResult(isValid, errors)
    }
}

