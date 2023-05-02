package br.com.anivicio.domain.show.ports.driven

import br.com.anivicio.domain.show.entity.ShowGenre
import java.util.*

interface FindingShowGenre {
    suspend fun byShowId(showId: UUID): List<ShowGenre>
}