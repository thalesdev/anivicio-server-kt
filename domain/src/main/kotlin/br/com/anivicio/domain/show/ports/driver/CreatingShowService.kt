package br.com.anivicio.domain.show.ports.driver

import br.com.anivicio.domain.show.entity.CompleteShow
import br.com.anivicio.domain.show.entity.ShowGenre
import br.com.anivicio.domain.show.entity.ShowType
import java.time.LocalDate
import java.util.*

interface CreatingShowService {
    suspend fun create(show: CreatingShowServiceCommand): CompleteShow?
}

data class CreatingShowServiceCommand(
    val name: String,
    val description: String,
    val showType: ShowType,
    val genres: List<ShowGenre>,
    val releaseDate: LocalDate,
    val mediaIds: List<UUID>,
    val castingPerformers: List<Casting>
) {
    fun toCreateShowCommand(): CreatingShowCommand {
        return CreatingShowCommand(name, description, showType, releaseDate)
    }

    fun toCreateShowMediaCommand(showId: UUID): CreatingShowMediaCommand {
        return CreatingShowMediaCommand(showId, mediaIds)
    }

    fun toCreateShowCastingCommand(showId: UUID): CreatingShowCastingCommand {
        return CreatingShowCastingCommand(showId, castingPerformers)
    }
}