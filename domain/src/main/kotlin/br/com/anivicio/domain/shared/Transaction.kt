package br.com.anivicio.domain.shared

interface Transaction {
    fun <T> execute(block: () -> T): T
    suspend fun <T> executeAsync(block: suspend () -> T): T
}