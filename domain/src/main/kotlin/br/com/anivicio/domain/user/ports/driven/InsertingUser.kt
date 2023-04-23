package br.com.anivicio.domain.user.ports.driven

import br.com.anivicio.domain.user.entity.User

interface InsertingUser {
    suspend fun insert(user: InsertUserData): User?
}

data class InsertUserData(
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val passwordSalt: String,
    val passwordHash: String
)