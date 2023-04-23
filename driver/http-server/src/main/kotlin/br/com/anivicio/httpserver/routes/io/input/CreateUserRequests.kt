package br.com.anivicio.httpserver.routes.io.input

import br.com.anivicio.domain.user.ports.driver.CreateUserCommand
import br.com.anivicio.httpserver.infra.Validation
import io.ktor.resources.*
import kotlinx.serialization.Serializable
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.validate

@Resource("/users")
@Serializable
data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String,
    val username: String
): Validation {
    fun toCommand() = CreateUserCommand(
        name = name,
        email = email,
        password = password,
        username = username
    )

    override fun valid() {
        validate(this) {
            validate(CreateUserRequest::name).hasSize(min = 3, max = 50)
            validate(CreateUserRequest::email).isEmail()
            validate(CreateUserRequest::email).hasSize(min = 3, max = 50)
            validate(CreateUserRequest::password).hasSize(min = 8, max = 32)
            validate(CreateUserRequest::username).hasSize(min = 3, max = 16)
        }
    }
}