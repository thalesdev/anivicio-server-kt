package br.com.anivicio.domain.shared.exceptions

open class BusinessException(val errorCode: BusinessErrorCode, reason:  Throwable?): Throwable(errorCode.message, reason) {}