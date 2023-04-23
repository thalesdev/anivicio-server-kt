package br.com.anivicio.domain.user.ports.driver

import br.com.anivicio.domain.user.entity.User

interface CreatingUser {
    suspend fun create(user: CreateUserCommand): User?
}


data class CreateUserCommand(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)