package br.com.anivicio.domain.shared.exceptions

fun <T> List<T>.someOrFail(error: Throwable): List<T> {
    return this.ifEmpty {
        throw error
    }
}