package br.com.anivicio.adapter.media.repository

import br.com.anivicio.adapter.media.entity.MediaItemEntity
import br.com.anivicio.adapter.show.entity.ShowMediaEntity
import br.com.anivicio.domain.media.entity.MediaItem
import br.com.anivicio.domain.media.ports.driven.FindingMediaItem
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select
import java.util.*

class MediaItemRepository : FindingMediaItem {
    override suspend fun byId(id: UUID): MediaItem? {
        return MediaItemEntity.select { MediaItemEntity.id eq id }.firstOrNull()?.let {
            return MediaItem(
                id = it[MediaItemEntity.id].value,
                ownerId = it[MediaItemEntity.ownerId].value,
                name = it[MediaItemEntity.name],
                description = it[MediaItemEntity.description],
                size = it[MediaItemEntity.size],
                url = it[MediaItemEntity.url],
                mediaType = it[MediaItemEntity.mediaType]
            )
        }
    }

    override suspend fun byIds(ids: List<UUID>): List<MediaItem> {
        return MediaItemEntity.select { MediaItemEntity.id inList ids }.map {
            MediaItem(
                id = it[MediaItemEntity.id].value,
                ownerId = it[MediaItemEntity.ownerId].value,
                name = it[MediaItemEntity.name],
                description = it[MediaItemEntity.description],
                size = it[MediaItemEntity.size],
                url = it[MediaItemEntity.url],
                mediaType = it[MediaItemEntity.mediaType]
            )
        }
    }

    override suspend fun byShowId(showId: UUID): List<MediaItem> {
        return MediaItemEntity
            .innerJoin(ShowMediaEntity, { MediaItemEntity.id }, { mediaId })
            .select { ShowMediaEntity.showId eq showId }.map {
                MediaItem(
                    id = it[MediaItemEntity.id].value,
                    ownerId = it[MediaItemEntity.ownerId].value,
                    name = it[MediaItemEntity.name],
                    description = it[MediaItemEntity.description],
                    size = it[MediaItemEntity.size],
                    url = it[MediaItemEntity.url],
                    mediaType = it[MediaItemEntity.mediaType]
                )
            }
    }
}