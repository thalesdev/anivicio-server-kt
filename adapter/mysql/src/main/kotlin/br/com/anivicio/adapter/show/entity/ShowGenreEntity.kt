package br.com.anivicio.adapter.show.entity

import br.com.anivicio.adapter.infra.TableWithUUID
import br.com.anivicio.adapter.infra.datetime
import br.com.anivicio.adapter.infra.enumColumn
import br.com.anivicio.adapter.infra.uuidColumn
import br.com.anivicio.domain.show.entity.ShowGenre

object ShowGenreEntity : TableWithUUID("show_genre") {
    val showId = uuidColumn("show_id").references(ShowEntity.id)
    val genre = enumColumn<ShowGenre>("genre")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}