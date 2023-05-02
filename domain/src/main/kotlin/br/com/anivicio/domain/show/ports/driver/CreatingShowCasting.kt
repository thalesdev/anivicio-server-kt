package br.com.anivicio.domain.show.ports.driver

import br.com.anivicio.domain.show.entity.ShowCastingRole
import br.com.anivicio.domain.show.ports.driven.InsertShowCastingPerformerData
import java.util.*

interface CreatingShowCasting {
    suspend fun create(show: CreatingShowCastingCommand)
}

sealed interface Casting {
    val role: ShowCastingRole
}
data class ExistingCasting(val id: UUID, override val role: ShowCastingRole): Casting
data class NewCasting(val name: String, override val role: ShowCastingRole, val imageId: UUID): Casting {
    fun toInsertShowCastingPerformerData(): InsertShowCastingPerformerData {
        return InsertShowCastingPerformerData(name, imageId)
    }
}

data class CreatingShowCastingCommand(
    val showId: UUID,
    val casting: List<Casting>
)