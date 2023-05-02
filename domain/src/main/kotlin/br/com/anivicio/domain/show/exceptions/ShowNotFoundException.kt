package br.com.anivicio.domain.show.exceptions

import br.com.anivicio.domain.shared.exceptions.BusinessErrorCode
import br.com.anivicio.domain.shared.exceptions.BusinessException

class ShowNotFoundException : BusinessException(BusinessErrorCode.SHOW_NOT_FOUND, null) {}