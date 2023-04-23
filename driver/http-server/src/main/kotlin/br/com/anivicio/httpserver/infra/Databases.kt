package br.com.anivicio.httpserver.infra

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun Application.configureDB() {
    val hikariDataSource = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://localhost:3306/anivicio"
        username = "root"
        password = "root"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }.let { config ->
        HikariDataSource(config)
    }

    Database.connect(hikariDataSource)
}
