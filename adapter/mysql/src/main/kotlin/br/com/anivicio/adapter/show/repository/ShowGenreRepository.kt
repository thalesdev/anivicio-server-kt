package br.com.anivicio.adapter.show.repository

import br.com.anivicio.adapter.show.entity.ShowGenreEntity
import br.com.anivicio.domain.show.entity.ShowGenre
import br.com.anivicio.domain.show.ports.driven.FindingShowGenre
import br.com.anivicio.domain.show.ports.driven.InsertingShowGenre
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select
import java.util.*

class ShowGenreRepository : InsertingShowGenre, FindingShowGenre {
    override fun insert(showId: UUID, genres: List<ShowGenre>) {
        ShowGenreEntity.batchInsert(genres) {
            this[ShowGenreEntity.id] = UUID.randomUUID()
            this[ShowGenreEntity.showId] = showId
            this[ShowGenreEntity.genre] = it
        }
    }

    override suspend fun byShowId(showId: UUID): List<ShowGenre> {
        return ShowGenreEntity.select { ShowGenreEntity.showId eq showId }.map {
            it[ShowGenreEntity.genre]
        }
    }
}