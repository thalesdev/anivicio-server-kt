package br.com.anivicio.domain.user.entity

import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
