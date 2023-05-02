package br.com.anivicio.domain.media.ports.driven

import br.com.anivicio.domain.media.entity.MediaItem
import java.util.*

interface FindingMediaItem {
    suspend fun byId(id: UUID): MediaItem?
    suspend fun byIds(ids: List<UUID>): List<MediaItem>

    suspend fun byShowId(showId: UUID): List<MediaItem>
}