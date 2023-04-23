package br.com.anivicio.domain.user.ports.driven

interface GettingPasswordHashed {
    suspend fun get(password: String): PasswordHashResult?
}

data class PasswordHashResult(
    val passwordSalt: String,
    val passwordHash: String
)