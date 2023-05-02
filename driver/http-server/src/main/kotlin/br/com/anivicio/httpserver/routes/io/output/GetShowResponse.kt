package br.com.anivicio.httpserver.routes.io.output

import br.com.anivicio.domain.show.entity.CompleteShow
import br.com.anivicio.domain.show.entity.ShowGenre
import br.com.anivicio.domain.show.entity.ShowType
import br.com.anivicio.shared.util.serializers.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*


@Serializable
data class GetShowResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val description: String,
    val showType: ShowType,
    val genre: List<ShowGenre>,
    @Contextual
    val releaseDate: LocalDate,
    val casting: List<ShowCastingResponse>,
    val media: List<ShowMediaItemResponse>
) {
    companion object {

        fun fromShow(show: CompleteShow): GetShowResponse {
            return GetShowResponse(
                id = show.id,
                name = show.name,
                description = show.description,
                showType = show.showType,
                genre = show.genre,
                releaseDate = show.releaseDate,
                casting = show.casting.map { casting ->
                    ShowCastingResponse(
                        id = casting.id,
                        role = casting.role,
                        performedBy = ShowCastingPerformerResponse(
                            id = casting.performedBy.id,
                            name = casting.performedBy.name,
                            image = ShowMediaItemResponse(
                                id = casting.performedBy.image.id,
                                ownerId = casting.performedBy.image.ownerId,
                                name = casting.performedBy.image.name,
                                description = casting.performedBy.image.description,
                                size = casting.performedBy.image.size,
                                url = casting.performedBy.image.url,
                                mediaType = casting.performedBy.image.mediaType
                            )
                        )
                    )
                },
                media = show.media.map { media ->
                    ShowMediaItemResponse(
                        id = media.id,
                        ownerId = media.ownerId,
                        name = media.name,
                        description = media.description,
                        size = media.size,
                        url = media.url,
                        mediaType = media.mediaType
                    )
                }
            )
        }
    }
}
