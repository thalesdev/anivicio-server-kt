package br.com.anivicio.domain.show.ports.driver

import br.com.anivicio.domain.show.entity.ShowType
import br.com.anivicio.domain.show.ports.driven.InsertingShowData
import java.time.LocalDate
import java.util.*

interface CreatingShow {
    suspend fun create(show: CreatingShowCommand): UUID
}


data class CreatingShowCommand(
    val name: String,
    val description: String,
    val showType: ShowType,
    val releaseDate: LocalDate,
) {
    fun toInsertingShowData() = InsertingShowData(
        name = name,
        description = description,
        showType = showType,
        releaseDate = releaseDate
    )
}