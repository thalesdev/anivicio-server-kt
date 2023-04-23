package br.com.anivicio.adapter

import br.com.anivicio.adapter.infra.ExposedTransaction
import br.com.anivicio.adapter.user.repository.UserRepository
import br.com.anivicio.domain.shared.Transaction
import br.com.anivicio.domain.user.ports.driven.FindingUser
import br.com.anivicio.domain.user.ports.driven.InsertingUser
import org.koin.dsl.module

val adapterMysqlEnvironment = module {
        single<Transaction> { ExposedTransaction() }

        single<InsertingUser> { UserRepository() }
        single<FindingUser> { UserRepository() }
}