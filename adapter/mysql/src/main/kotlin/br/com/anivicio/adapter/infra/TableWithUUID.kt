package br.com.anivicio.adapter.infra

import org.jetbrains.exposed.dao.id.IdTable
import java.util.UUID

open class TableWithUUID(name: String, columnName: String = "id") : IdTable<UUID>(name) {
    final override val id = uuidColumn(columnName).entityId()
    final override val primaryKey = PrimaryKey(id)
}