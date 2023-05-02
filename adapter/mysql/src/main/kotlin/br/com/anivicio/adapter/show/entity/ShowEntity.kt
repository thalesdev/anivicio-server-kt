package br.com.anivicio.adapter.show.entity

import br.com.anivicio.adapter.infra.TableWithUUID
import br.com.anivicio.adapter.infra.date
import br.com.anivicio.adapter.infra.datetime
import br.com.anivicio.adapter.infra.enumColumn
import br.com.anivicio.domain.show.entity.ShowType

object ShowEntity : TableWithUUID("show") {
    val name = varchar("name", 255)
    val description = mediumText("description")
    val showType = enumColumn<ShowType>("show_type")
    val releaseDate = date("release_date")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}