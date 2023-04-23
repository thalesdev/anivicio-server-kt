package br.com.anivicio.httpserver.infra

import br.com.anivicio.adapter.adapterMysqlEnvironment
import br.com.anivicio.adapter.crypto.cryptoEnv
import br.com.anivicio.domain.user.userModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.dsl.module


val environmentModule = module {
    includes(userModule)
    includes(adapterMysqlEnvironment)
    includes(cryptoEnv)
}


fun Application.environment() {
    install(Koin) {
        modules(environmentModule)
    }
}

