package br.com.anivicio.domain.shared.exceptions

enum class BusinessErrorCode(val message: String) {
    USER_ALREADY_EXISTS("User already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    SOME_MEDIA_NOT_FOUND("Some media not found"),
    SHOW_NOT_FOUND("Show not found"),
    SHOW_CASTING_NOT_FOUND("Show casting not found"),
}