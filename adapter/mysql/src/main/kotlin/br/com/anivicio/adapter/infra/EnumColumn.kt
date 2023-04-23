package br.com.anivicio.adapter.infra


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import kotlin.reflect.KClass

class EnumColumType<T : Enum<T>>(type: KClass<T>, override var nullable: Boolean) : ColumnType() {
    override fun sqlType(): String = currentDialect.dataTypeProvider.varcharType(255)
    private val enumValues = type.java.enumConstants

    override fun valueFromDB(value: Any): Any = when (value) {
        is String -> enumValues.first { it.name == value }
        is Enum<*> -> enumValues[value.ordinal]
        else -> throw IllegalArgumentException()
    }

    override fun valueToDB(value: Any?): String = when (value) {
        is Enum<*> -> value.name
        is String -> value
        else -> throw IllegalArgumentException()
    }

    companion object {
        inline operator fun <reified T : Enum<T>> invoke(nullable: Boolean = false): EnumColumType<T> =
            EnumColumType(T::class, nullable)
    }
}

inline fun <reified T : Enum<T>> Table.enumColumn(name: String, nullable: Boolean = false): Column<T> =
    registerColumn(name, EnumColumType(T::class, nullable))