package br.com.anivicio.domain.user.exceptions

import br.com.anivicio.domain.shared.exceptions.BusinessErrorCode
import br.com.anivicio.domain.shared.exceptions.BusinessException

class EmailAlreadyExistsException: BusinessException(BusinessErrorCode.EMAIL_ALREADY_EXISTS, null) {}