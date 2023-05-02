package br.com.anivicio.domain.show.ports.driven

import java.util.*

interface InsertingShowMedia {
    suspend fun insert(data: List<InsertingShowMediaData>)
}
data class InsertingShowMediaData(
    val showId: UUID,
    val mediaId: UUID
)