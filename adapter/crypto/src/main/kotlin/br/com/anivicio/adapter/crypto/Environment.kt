package br.com.anivicio.adapter.crypto

import br.com.anivicio.adapter.crypto.hashing.HashingPasswordHandler
import br.com.anivicio.domain.user.ports.driven.GettingPasswordHashed
import org.koin.dsl.module

val cryptoEnv = module {
    single<GettingPasswordHashed> { HashingPasswordHandler() }
}