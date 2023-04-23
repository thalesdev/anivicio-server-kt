package br.com.anivicio.adapter.infra

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.util.UUID

class UUIDColumnTypeString(override var nullable: Boolean) : ColumnType() {
    override fun sqlType(): String = currentDialect.dataTypeProvider.varcharType(36)

    override fun valueFromDB(value: Any): UUID =
        when {
            value is UUID -> value
            value is String && value.matches(uuidRegexp) -> UUID.fromString(value)
            else -> throw IllegalArgumentException()
        }

    override fun valueToDB(value: Any?): String? = run {
        if (value == null && nullable) return null
        return when (value) {
            is UUID -> value.toString()
            is String -> value
            else -> throw IllegalArgumentException()
        }
    }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is UUID -> value.toString()
        is String -> value
        else -> throw IllegalArgumentException()
    }

    companion object {
        private val uuidRegexp =
            Regex("[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}", RegexOption.IGNORE_CASE)

        operator fun invoke(nullable: Boolean = false): UUIDColumnTypeString = UUIDColumnTypeString(nullable)
    }
}

fun Table.uuidColumn(name: String): Column<UUID> = registerColumn(name, UUIDColumnTypeString())
fun Table.uuidNullableColumn(name: String): Column<UUID> = registerColumn(name, UUIDColumnTypeString(true))