package br.com.anivicio.domain.show.ports.driven

import br.com.anivicio.domain.show.entity.ShowCastingRole
import java.util.*

interface InsertingShowCasting {
    suspend fun insert(data: List<InsertShowCastingData>)
}

data class InsertShowCastingData(
    val showId: UUID,
    val performerId: UUID,
    val role: ShowCastingRole
)