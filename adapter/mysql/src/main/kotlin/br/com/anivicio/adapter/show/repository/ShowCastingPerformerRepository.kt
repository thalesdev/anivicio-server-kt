package br.com.anivicio.adapter.show.repository

import br.com.anivicio.adapter.show.entity.ShowCastingPerformerEntity
import br.com.anivicio.domain.show.ports.driven.InsertShowCastingPerformerData
import br.com.anivicio.domain.show.ports.driven.InsertingShowCastingPerformer
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import java.util.*

class ShowCastingPerformerRepository : InsertingShowCastingPerformer {
    override suspend fun insert(data: InsertShowCastingPerformerData): UUID {
        return ShowCastingPerformerEntity.insertAndGetId {
            it[id] = UUID.randomUUID()
            it[name] = data.name
            it[mediaId] = data.mediaId
        }.value
    }

    override suspend fun insertBulk(data: List<InsertShowCastingPerformerData>): List<UUID> {
        return ShowCastingPerformerEntity.batchInsert(data) {
            this[ShowCastingPerformerEntity.id] = UUID.randomUUID()
            this[ShowCastingPerformerEntity.name] = it.name
            this[ShowCastingPerformerEntity.mediaId] = it.mediaId
        }.map { it[ShowCastingPerformerEntity.id].value }
    }
}