package br.com.anivicio.domain.user.usecases

import br.com.anivicio.domain.shared.Transaction
import br.com.anivicio.domain.user.entity.User
import br.com.anivicio.domain.user.exceptions.EmailAlreadyExistsException
import br.com.anivicio.domain.user.exceptions.UserAlreadyExistsException
import br.com.anivicio.domain.user.ports.driven.FindingUser
import br.com.anivicio.domain.user.ports.driven.GettingPasswordHashed
import br.com.anivicio.domain.user.ports.driven.InsertUserData
import br.com.anivicio.domain.user.ports.driven.InsertingUser
import br.com.anivicio.domain.user.ports.driver.CreateUserCommand
import br.com.anivicio.domain.user.ports.driver.CreatingUser


class CreateUser(
    private val transaction: Transaction,
    private val insertingUser: InsertingUser,
    private val getPasswordHashed: GettingPasswordHashed,
    private val findingUser: FindingUser
) : CreatingUser {
    override suspend fun create(user: CreateUserCommand): User? {
        return transaction.executeAsync {
            checkingUserExists(user)
            checkingEmailExists(user)

            val passwordHashed = getPasswordHashed.get(user.password) ?: return@executeAsync null

            insertingUser.insert(
                InsertUserData(
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    username = user.username,
                    passwordHash = passwordHashed.passwordHash,
                    passwordSalt = passwordHashed.passwordSalt
                )
            )
        }
    }

    private suspend fun checkingUserExists(user: CreateUserCommand) {
        val userExists = findingUser.findByUsername(user.username)
        if (userExists != null) {
            throw UserAlreadyExistsException()
        }
    }

    private suspend fun checkingEmailExists(user: CreateUserCommand) {
        val emailExists = findingUser.findByEmail(user.email)
        if (emailExists != null) {
            throw EmailAlreadyExistsException()
        }
    }
}