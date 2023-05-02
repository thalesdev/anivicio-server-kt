package br.com.anivicio.adapter.show.repository

import br.com.anivicio.adapter.show.entity.ShowMediaEntity
import br.com.anivicio.domain.show.ports.driven.InsertingShowMedia
import br.com.anivicio.domain.show.ports.driven.InsertingShowMediaData
import org.jetbrains.exposed.sql.batchInsert

class ShowMediaRepository : InsertingShowMedia {
    override suspend fun insert(data: List<InsertingShowMediaData>) {
        ShowMediaEntity.batchInsert(data) {
            this[ShowMediaEntity.showId] = it.showId
            this[ShowMediaEntity.mediaId] = it.mediaId
        }
    }
}