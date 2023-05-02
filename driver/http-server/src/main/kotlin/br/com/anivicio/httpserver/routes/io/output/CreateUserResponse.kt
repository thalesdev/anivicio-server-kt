package br.com.anivicio.httpserver.routes.io.output

import br.com.anivicio.domain.user.entity.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class CreateUserResponse(
    @Contextual
    val id: UUID,
    val name: String,
    val email: String,
    val username: String,
    @Contextual
    val createdAt: LocalDateTime,
    @Contextual
    val updatedAt: LocalDateTime
) {
    companion object {
        fun fromUser(command: User) = CreateUserResponse(
            id = command.id,
            name = command.name,
            email = command.email,
            username = command.username,
            createdAt = command.createdAt,
            updatedAt = command.updatedAt
        )
    }
}