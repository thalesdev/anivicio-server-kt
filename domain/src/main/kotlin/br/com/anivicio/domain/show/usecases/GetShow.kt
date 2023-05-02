package br.com.anivicio.domain.show.usecases

import br.com.anivicio.domain.media.ports.driven.FindingMediaItem
import br.com.anivicio.domain.shared.Transaction
import br.com.anivicio.domain.show.entity.CompleteShow
import br.com.anivicio.domain.show.exceptions.ShowNotFoundException
import br.com.anivicio.domain.show.ports.driven.FindingShow
import br.com.anivicio.domain.show.ports.driven.FindingShowCasting
import br.com.anivicio.domain.show.ports.driven.FindingShowGenre
import br.com.anivicio.domain.show.ports.driver.GettingShow
import java.util.*

class GetShow(
    private val findingShow: FindingShow,
    private val findingMediaItem: FindingMediaItem,
    private val findingShowCasting: FindingShowCasting,
    private val findingShowGenre: FindingShowGenre,
    private val transaction: Transaction
) : GettingShow {
    override suspend fun get(id: UUID): CompleteShow {
        return transaction.executeAsync {
            val basicShow = findingShow.basicById(id) ?: throw ShowNotFoundException()
            val medias = findingMediaItem.byShowId(id)
            val casting = findingShowCasting.byShowId(id)
            val genres = findingShowGenre.byShowId(id)


            return@executeAsync CompleteShow(
                id = basicShow.id,
                name = basicShow.name,
                description = basicShow.description,
                showType = basicShow.showType,
                genre = genres,
                releaseDate = basicShow.releaseDate,
                casting = casting,
                media = medias
            )
        }
    }
}