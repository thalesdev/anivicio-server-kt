package br.com.anivicio.adapter.show.entity

import br.com.anivicio.adapter.infra.TableWithUUID
import br.com.anivicio.adapter.infra.datetime
import br.com.anivicio.adapter.infra.enumColumn
import br.com.anivicio.adapter.infra.uuidColumn
import br.com.anivicio.domain.show.entity.ShowCastingRole

object ShowCastingEntity : TableWithUUID("show_casting") {
    val showId = uuidColumn("show_id").references(ShowEntity.id)
    val performerId = uuidColumn("performer_id").references(ShowCastingPerformerEntity.id)
    val role = enumColumn<ShowCastingRole>("role")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}