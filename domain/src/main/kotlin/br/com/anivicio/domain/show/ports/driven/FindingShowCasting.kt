package br.com.anivicio.domain.show.ports.driven

import br.com.anivicio.domain.show.entity.ShowCasting
import java.util.*

interface FindingShowCasting {
   suspend fun byShowId(showId: UUID): List<ShowCasting>
}