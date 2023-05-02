package br.com.anivicio.domain.show.ports.driver

import java.util.*

interface CreatingShowMedia {
    suspend fun create(showMedia: CreatingShowMediaCommand)
}

data class CreatingShowMediaCommand(
    val showId: UUID,
    val mediaIds: List<UUID>
)