package br.com.anivicio.domain.show.exceptions

import br.com.anivicio.domain.shared.exceptions.BusinessErrorCode
import br.com.anivicio.domain.shared.exceptions.BusinessException

class SomeMediaNotFoundException: BusinessException(BusinessErrorCode.SOME_MEDIA_NOT_FOUND, null) {}