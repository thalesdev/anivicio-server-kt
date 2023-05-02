package br.com.anivicio.domain.show.ports.driven

import br.com.anivicio.domain.show.entity.BasicShow
import java.util.*

interface FindingShow {
    suspend fun basicById(id: UUID): BasicShow?
}