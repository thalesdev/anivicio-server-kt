package br.com.anivicio.domain.show.usecases

import br.com.anivicio.domain.show.ports.driven.InsertingShow
import br.com.anivicio.domain.show.ports.driver.CreatingShow
import br.com.anivicio.domain.show.ports.driver.CreatingShowCommand
import java.util.*

class CreateShow(
    private val insertingShow: InsertingShow
): CreatingShow {
    override suspend fun create(show: CreatingShowCommand): UUID {
      return insertingShow.insert(show.toInsertingShowData())
    }
}