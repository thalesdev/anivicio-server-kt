package br.com.anivicio.domain.show.usecases

import br.com.anivicio.domain.show.entity.ShowCastingRole
import br.com.anivicio.domain.show.ports.driven.InsertShowCastingData
import br.com.anivicio.domain.show.ports.driven.InsertingShowCasting
import br.com.anivicio.domain.show.ports.driven.InsertingShowCastingPerformer
import br.com.anivicio.domain.show.ports.driver.CreatingShowCasting
import br.com.anivicio.domain.show.ports.driver.CreatingShowCastingCommand
import br.com.anivicio.domain.show.ports.driver.ExistingCasting
import br.com.anivicio.domain.show.ports.driver.NewCasting
import java.util.*

class CreateShowCasting(
    private val insertingShowCastingPerformer: InsertingShowCastingPerformer,
    private val insertingShowCasting: InsertingShowCasting
): CreatingShowCasting {
    override suspend fun create(show: CreatingShowCastingCommand) {
        val toInsert = show.casting.filterIsInstance<NewCasting>()
        val performersInserted = insertNewPerformers(toInsert)
        val performersAlreadyCreated = show.casting.filterIsInstance<ExistingCasting>().map { it.id to it.role }
        val toInsertShowCasting = (performersInserted + performersAlreadyCreated).map{
            InsertShowCastingData(show.showId, it.first, it.second)
        }

         insertingShowCasting.insert(toInsertShowCasting)
    }


    private suspend fun insertNewPerformers(toInsert: List<NewCasting>): List<Pair<UUID, ShowCastingRole>> {
        val ids =  insertingShowCastingPerformer.insertBulk(toInsert.map { it.toInsertShowCastingPerformerData() })
        return toInsert.zip(ids).map { (newCasting, id) -> id to newCasting.role }
    }

}