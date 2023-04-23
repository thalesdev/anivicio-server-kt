package br.com.anivicio.adapter.crypto.hashing

import br.com.anivicio.domain.user.ports.driven.GettingPasswordHashed
import br.com.anivicio.domain.user.ports.driven.PasswordHashResult
import de.mkammerer.argon2.Argon2Factory
import java.security.SecureRandom
import java.util.Base64

class HashingPasswordHandler: GettingPasswordHashed {
    private val argonFactory = Argon2Factory.create()
    private val secureRandom = SecureRandom()

    override suspend fun get(password: String): PasswordHashResult? {
        val ba = ByteArray(26)
        secureRandom.nextBytes(ba)
        val salt = Base64.getEncoder().encodeToString(ba)
        val passwordChar = "$password$salt".toCharArray()
        val passwordHashed = argonFactory.hash(10, 1024, 4, passwordChar)
        return PasswordHashResult(passwordHashed, salt)
    }
}