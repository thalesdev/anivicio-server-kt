package br.com.anivicio.domain.show.ports.driven

import br.com.anivicio.domain.show.entity.ShowGenre
import java.util.*

interface InsertingShowGenre {
    fun insert(showId: UUID, genres: List<ShowGenre>)
}