package br.com.anivicio.domain.show.usecases

import br.com.anivicio.domain.show.ports.driven.InsertingShowMedia
import br.com.anivicio.domain.show.ports.driven.InsertingShowMediaData
import br.com.anivicio.domain.show.ports.driver.CreatingShowMedia
import br.com.anivicio.domain.show.ports.driver.CreatingShowMediaCommand

class CreateShowMedia(
    private val insertingShowMedia: InsertingShowMedia
): CreatingShowMedia {
    override suspend fun create(showMedia: CreatingShowMediaCommand) {
        insertingShowMedia.insert(showMedia.mediaIds.map { mediaId ->
            InsertingShowMediaData(
                showId = showMedia.showId,
                mediaId = mediaId
            )
        })
    }
}