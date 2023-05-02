package br.com.anivicio.domain.media.entity

import java.util.*

data class MediaItem(
    val id: UUID,
    val ownerId: UUID,
    val name: String,
    val description: String,
    val size: Long,
    val url: String,
    val mediaType: MediaType,
)
