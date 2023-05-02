package br.com.anivicio.httpserver.plugins

import br.com.anivicio.domain.shared.exceptions.BusinessException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import java.util.logging.Logger

@Serializable
data class ErrorResponse(val message: String, val errorCode: String)

fun Application.configureRouting() {

    val logger = Logger.getLogger("ktor.application")

    install(Resources)

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is BusinessException -> {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(cause.message ?: "Bad Request", cause.errorCode.toString())
                    )
                    return@exception
                }

                else -> {
                    logger.severe(cause.message)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse(
                            "Internal component failed while handling request. Please try again later.",
                            "DEPENDENCY_FAILURE"
                        )
                    )
                    return@exception
                }
            }
        }
    }
}