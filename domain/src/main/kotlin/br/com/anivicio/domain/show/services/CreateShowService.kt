package br.com.anivicio.domain.show.services

import br.com.anivicio.domain.media.entity.MediaItem
import br.com.anivicio.domain.media.ports.driven.FindingMediaItem
import br.com.anivicio.domain.shared.Transaction
import br.com.anivicio.domain.show.entity.CompleteShow
import br.com.anivicio.domain.show.entity.ShowCasting
import br.com.anivicio.domain.show.exceptions.SomeMediaNotFoundException
import br.com.anivicio.domain.show.ports.driven.FindingShowCasting
import br.com.anivicio.domain.show.ports.driven.InsertingShowGenre
import br.com.anivicio.domain.show.ports.driver.*
import java.util.*

class CreateShowService(
    private val creatingShow: CreatingShow,
    private val creatingShowCasting: CreatingShowCasting,
    private val creatingShowMedia: CreatingShowMedia,
    private val findingMediaItem: FindingMediaItem,
    private val findingShowCasting: FindingShowCasting,
    private val insertingShowGenre: InsertingShowGenre,
    private val transaction: Transaction
) : CreatingShowService {
    override suspend fun create(show: CreatingShowServiceCommand): CompleteShow? {
        // TODO: add session identity validation
        return transaction.executeAsync {
            val showId = creatingShow.create(show.toCreateShowCommand()) ?: return@executeAsync null

            addGenres(show, showId)

            val media = createShowMedia(show, showId)
            val showCasting = createShowCasting(show, showId)

            return@executeAsync CompleteShow(
                showId,
                show.name,
                show.description,
                show.showType,
                show.genres,
                show.releaseDate,
                showCasting,
                media
            )
        }

    }


    private suspend fun createShowMedia(show: CreatingShowServiceCommand, showId: UUID): List<MediaItem> {

        val medias = findingMediaItem.byIds(show.mediaIds)
        if (medias.size != show.mediaIds.size) throw SomeMediaNotFoundException()

        creatingShowMedia.create(show.toCreateShowMediaCommand(showId))

        return medias
    }

    private suspend fun createShowCasting(show: CreatingShowServiceCommand, showId: UUID): List<ShowCasting> {
        creatingShowCasting.create(show.toCreateShowCastingCommand(showId))
        return findingShowCasting.byShowId(showId)
    }

    private suspend fun addGenres(show: CreatingShowServiceCommand, showId: UUID) {
        insertingShowGenre.insert(showId, show.genres)
    }
}