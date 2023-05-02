package br.com.anivicio.adapter.show.entity

import br.com.anivicio.adapter.infra.datetime
import br.com.anivicio.adapter.infra.uuidColumn
import br.com.anivicio.adapter.media.entity.MediaItemEntity
import org.jetbrains.exposed.sql.Table

object ShowMediaEntity : Table("show_media") {
    val showId = uuidColumn("show_id").references(ShowEntity.id)
    val mediaId = uuidColumn("media_id").references(MediaItemEntity.id)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}