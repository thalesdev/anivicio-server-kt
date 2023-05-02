package br.com.anivicio.domain.show.ports.driven

import br.com.anivicio.domain.show.entity.ShowType
import java.time.LocalDate
import java.util.*

interface InsertingShow {
    suspend fun insert(data: InsertingShowData): UUID
}

data class InsertingShowData(
    val name: String,
    val description: String,
    val showType: ShowType,
    val releaseDate: LocalDate,
)