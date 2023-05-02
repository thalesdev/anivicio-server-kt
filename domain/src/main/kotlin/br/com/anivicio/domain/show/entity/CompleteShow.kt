package br.com.anivicio.domain.show.entity

import br.com.anivicio.domain.media.entity.MediaItem
import java.time.LocalDate
import java.util.*


sealed interface Show {
    val id: UUID
    val name: String
    val description: String
    val showType: ShowType
    val releaseDate: LocalDate
}

data class BasicShow(
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val showType: ShowType,
    override val releaseDate: LocalDate
) : Show

data class CompleteShow(
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val showType: ShowType,
    val genre: List<ShowGenre>,
    override val releaseDate: LocalDate,
    val casting: List<ShowCasting>,
    val media: List<MediaItem>
) : Show
