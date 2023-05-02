package br.com.anivicio.domain.show.ports.driver

import br.com.anivicio.domain.show.entity.CompleteShow
import java.util.*

interface GettingShow {
    suspend fun get(id: UUID): CompleteShow
}