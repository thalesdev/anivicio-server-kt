package br.com.anivicio.domain.user

import br.com.anivicio.domain.user.ports.driver.CreatingUser
import br.com.anivicio.domain.user.usecases.CreateUser
import org.koin.dsl.module

val userModule = module {
    single<CreatingUser> { CreateUser(get(), get(), get(), get()) }
}