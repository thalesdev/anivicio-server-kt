package br.com.anivicio.httpserver.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

val BASE_ROUTE = "anivicio/api/"


fun Application.routes() {
    routing {
        userRoutes()
    }
}