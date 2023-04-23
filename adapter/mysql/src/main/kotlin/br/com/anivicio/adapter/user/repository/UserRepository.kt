package br.com.anivicio.adapter.user.repository

import br.com.anivicio.adapter.user.entity.UserEntity
import br.com.anivicio.domain.user.entity.User
import br.com.anivicio.domain.user.ports.driven.FindingUser
import br.com.anivicio.domain.user.ports.driven.InsertUserData
import br.com.anivicio.domain.user.ports.driven.InsertingUser
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import java.util.UUID

class UserRepository: InsertingUser, FindingUser {
    override suspend fun insert(user: InsertUserData): User? {
        val id = UserEntity.insertAndGetId {
            it[id] = UUID.randomUUID()
            it[name] = user.name
            it[username] = user.username
            it[email] = user.email
            it[passwordHash] = user.passwordHash
            it[passwordSalt] = user.passwordSalt
        }

        return UserEntity.select { UserEntity.id eq id }.map {
            User(
                it[UserEntity.id].value,
                it[UserEntity.name],
                it[UserEntity.username],
                it[UserEntity.email],
                it[UserEntity.createdAt],
                it[UserEntity.updatedAt]
            )
        }.singleOrNull()
    }

    override suspend fun findByUsername(username: String): User? {
        return UserEntity.select { UserEntity.username eq username }.map {
            User(
                it[UserEntity.id].value,
                it[UserEntity.name],
                it[UserEntity.username],
                it[UserEntity.email],
                it[UserEntity.createdAt],
                it[UserEntity.updatedAt]
            )
        }.singleOrNull()
    }

    override suspend fun findByEmail(email: String): User? {
        return UserEntity.select { UserEntity.email eq email }.map {
            User(
                it[UserEntity.id].value,
                it[UserEntity.name],
                it[UserEntity.username],
                it[UserEntity.email],
                it[UserEntity.createdAt],
                it[UserEntity.updatedAt]
            )
        }.singleOrNull()
    }
}