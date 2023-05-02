package br.com.anivicio.domain.show.entity

import java.util.*

data class ShowCasting(
    val id: UUID,
    val role: ShowCastingRole,
    val performedBy: ShowCastingPerformer
)
