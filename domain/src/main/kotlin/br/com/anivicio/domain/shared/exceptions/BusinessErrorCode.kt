package br.com.anivicio.domain.shared.exceptions

enum class BusinessErrorCode(val message: String) {
    USER_ALREADY_EXISTS("User already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists")
}