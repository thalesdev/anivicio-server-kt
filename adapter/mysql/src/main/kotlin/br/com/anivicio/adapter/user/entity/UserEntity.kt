package br.com.anivicio.adapter.user.entity

import br.com.anivicio.adapter.infra.TableWithUUID
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserEntity: TableWithUUID("users") {
    val name = varchar("name", 50)

    val username = varchar("username", 16)
    val email = varchar("email", 50)

    val passwordHash = varchar("password_hash", 255)
    val passwordSalt = varchar("password_salt", 255)

    val createdAt = datetime("created_at").clientDefault {
        LocalDateTime.now()
    }
    val updatedAt = datetime("updated_at").clientDefault {
        LocalDateTime.now()
    }


    val usernameIndex = uniqueIndex("username", username)
    val emailIndex = uniqueIndex("email", email)
}