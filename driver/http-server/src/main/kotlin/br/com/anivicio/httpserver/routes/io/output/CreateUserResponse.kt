package br.com.anivicio.httpserver.routes.io.output

import br.com.anivicio.domain.user.entity.User
import br.com.anivicio.shared.util.serializers.LocalDateTimeSerializer
import br.com.anivicio.shared.util.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class CreateUserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val email: String,
    val username: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
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