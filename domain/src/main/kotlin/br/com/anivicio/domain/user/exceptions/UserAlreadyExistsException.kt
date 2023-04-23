package br.com.anivicio.domain.user.exceptions

import br.com.anivicio.domain.shared.exceptions.BusinessErrorCode
import br.com.anivicio.domain.shared.exceptions.BusinessException

class UserAlreadyExistsException: BusinessException(BusinessErrorCode.USER_ALREADY_EXISTS, null) {}