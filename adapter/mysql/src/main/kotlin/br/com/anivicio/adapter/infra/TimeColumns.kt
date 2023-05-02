package br.com.anivicio.adapter.infra

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

fun Table.datetime(name: String) = datetime(name).clientDefault {
    java.time.LocalDateTime.now()
}

fun TableWithUUID.date(name: String) = date(name).clientDefault {
    java.time.LocalDate.now()
}





