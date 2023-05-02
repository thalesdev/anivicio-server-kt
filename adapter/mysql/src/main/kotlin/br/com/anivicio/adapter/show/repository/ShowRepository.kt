package br.com.anivicio.adapter.show.repository

import br.com.anivicio.adapter.show.entity.ShowEntity
import br.com.anivicio.domain.show.entity.BasicShow
import br.com.anivicio.domain.show.ports.driven.FindingShow
import br.com.anivicio.domain.show.ports.driven.InsertingShow
import br.com.anivicio.domain.show.ports.driven.InsertingShowData
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import java.util.*

class ShowRepository : InsertingShow, FindingShow {
    override suspend fun insert(data: InsertingShowData): UUID {
        return ShowEntity.insertAndGetId {
            it[id] = UUID.randomUUID()
            it[name] = data.name
            it[showType] = data.showType
            it[description] = data.description
            it[releaseDate] = data.releaseDate
        }.value
    }

    override suspend fun basicById(id: UUID): BasicShow? {
        return ShowEntity.select { ShowEntity.id eq id }.firstOrNull()?.let {
            return BasicShow(
                id = it[ShowEntity.id].value,
                name = it[ShowEntity.name],
                description = it[ShowEntity.description],
                showType = it[ShowEntity.showType],
                releaseDate = it[ShowEntity.releaseDate]
            )
        }
    }
}