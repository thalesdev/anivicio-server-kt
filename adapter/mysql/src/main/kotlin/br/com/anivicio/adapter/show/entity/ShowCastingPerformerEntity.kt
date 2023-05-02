package br.com.anivicio.adapter.show.entity

import br.com.anivicio.adapter.infra.TableWithUUID
import br.com.anivicio.adapter.infra.datetime
import br.com.anivicio.adapter.media.entity.MediaItemEntity

object ShowCastingPerformerEntity : TableWithUUID("show_casting_performer") {
    val name = varchar("name", 255)
    val mediaId = reference("media_id", MediaItemEntity.id)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}