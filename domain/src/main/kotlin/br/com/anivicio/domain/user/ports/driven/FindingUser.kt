package br.com.anivicio.domain.user.ports.driven

import br.com.anivicio.domain.user.entity.User

interface FindingUser {
    suspend fun findByUsername(username: String): User?
    suspend fun findByEmail(email: String): User?
}