package br.com.anivicio.adapter.show.repository

import br.com.anivicio.adapter.media.entity.MediaItemEntity
import br.com.anivicio.adapter.show.entity.ShowCastingEntity
import br.com.anivicio.adapter.show.entity.ShowCastingPerformerEntity
import br.com.anivicio.domain.media.entity.MediaItem
import br.com.anivicio.domain.show.entity.ShowCasting
import br.com.anivicio.domain.show.entity.ShowCastingPerformer
import br.com.anivicio.domain.show.ports.driven.FindingShowCasting
import br.com.anivicio.domain.show.ports.driven.InsertShowCastingData
import br.com.anivicio.domain.show.ports.driven.InsertingShowCasting
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select
import java.util.*

class ShowCastingRepository : FindingShowCasting, InsertingShowCasting {
    override suspend fun byShowId(showId: UUID): List<ShowCasting> {
        return ShowCastingEntity
            .innerJoin(ShowCastingPerformerEntity)
            .innerJoin(MediaItemEntity, { ShowCastingPerformerEntity.mediaId }, { MediaItemEntity.id })
            .select { ShowCastingEntity.showId eq showId }
            .map {
                ShowCasting(
                    id = it[ShowCastingEntity.id].value,
                    role = it[ShowCastingEntity.role],
                    performedBy = ShowCastingPerformer(
                        id = it[ShowCastingPerformerEntity.id].value,
                        name = it[ShowCastingPerformerEntity.name],
                        image = MediaItem(
                            id = it[MediaItemEntity.id].value,
                            url = it[MediaItemEntity.url],
                            name = it[MediaItemEntity.name],
                            mediaType = it[MediaItemEntity.mediaType],
                            size = it[MediaItemEntity.size],
                            description = it[MediaItemEntity.description],
                            ownerId = it[MediaItemEntity.ownerId].value,
                        )
                    ),
                )
            }
    }

    override suspend fun insert(data: List<InsertShowCastingData>) {
        ShowCastingEntity.batchInsert(data) {
            this[ShowCastingEntity.id] = UUID.randomUUID()
            this[ShowCastingEntity.showId] = it.showId
            this[ShowCastingEntity.role] = it.role
            this[ShowCastingEntity.performerId] = it.performerId
        }
    }
}