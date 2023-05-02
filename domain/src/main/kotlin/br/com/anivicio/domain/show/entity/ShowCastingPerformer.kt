package br.com.anivicio.domain.show.entity

import br.com.anivicio.domain.media.entity.MediaItem
import java.util.*

data class ShowCastingPerformer(
    val id: UUID,
    val name: String,
    val image: MediaItem
)
