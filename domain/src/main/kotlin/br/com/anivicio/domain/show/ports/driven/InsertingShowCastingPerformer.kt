package br.com.anivicio.domain.show.ports.driven

import java.util.*

interface InsertingShowCastingPerformer {
    suspend fun insert(data: InsertShowCastingPerformerData): UUID

    suspend fun insertBulk(data: List<InsertShowCastingPerformerData>): List<UUID>
}

data class InsertShowCastingPerformerData(
    val name: String,
    val mediaId: UUID
)