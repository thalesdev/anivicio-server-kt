package br.com.anivicio.adapter.media.entity

import br.com.anivicio.adapter.infra.TableWithUUID
import br.com.anivicio.adapter.infra.enumColumn
import br.com.anivicio.adapter.user.entity.UserEntity
import br.com.anivicio.domain.media.entity.MediaType

object MediaItemEntity : TableWithUUID("media_item") {
    val ownerId = reference("owner_id", UserEntity.id)
    val name = varchar("name", 255)
    val description = mediumText("description")
    val size = long("size")
    val url = varchar("url", 255)
    val mediaType = enumColumn<MediaType>("media_type")
}