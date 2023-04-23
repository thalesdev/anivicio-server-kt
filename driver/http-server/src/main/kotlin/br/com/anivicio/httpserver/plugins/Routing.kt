package br.com.anivicio.httpserver.plugins

import br.com.anivicio.domain.shared.exceptions.BusinessException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String, val errorCode: String)

fun Application.configureRouting() {
    install(Resources)

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when(cause) {
                is BusinessException -> {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(cause.message ?: "Bad Request", cause.errorCode.toString())
                    )
                    return@exception
                }
                else -> {
                    call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
                    return@exception
                }
            }
        }
    }
}