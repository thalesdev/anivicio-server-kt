package br.com.anivicio.httpserver

import br.com.anivicio.httpserver.infra.configureDB
import br.com.anivicio.httpserver.infra.environment
import br.com.anivicio.httpserver.plugins.*
import br.com.anivicio.httpserver.routes.routes
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    environment()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureDB()
    configureSockets()
    configureRouting()
    routes()
}
