package br.com.anivicio.domain.show.exceptions

import br.com.anivicio.domain.shared.exceptions.BusinessErrorCode
import br.com.anivicio.domain.shared.exceptions.BusinessException

class ShowCastingNotFoundException : BusinessException(BusinessErrorCode.SHOW_CASTING_NOT_FOUND, null) {}