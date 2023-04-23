package br.com.anivicio.adapter.infra

import br.com.anivicio.domain.shared.Transaction
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedTransaction: Transaction {
    override fun <T> execute(block: () -> T): T {
        return transaction {
            block()
        }
    }

    override suspend fun <T> executeAsync(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) { block() }
    }
}