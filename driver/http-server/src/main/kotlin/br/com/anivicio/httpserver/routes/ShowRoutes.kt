package br.com.anivicio.httpserver.routes

import br.com.anivicio.domain.show.ports.driver.CreatingShowService
import br.com.anivicio.domain.show.ports.driver.GettingShow
import br.com.anivicio.httpserver.routes.io.input.CreateShowRequest
import br.com.anivicio.httpserver.routes.io.output.CreateShowResponse
import br.com.anivicio.httpserver.routes.io.output.GetShowResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.showRoutes() {

    val createShow: CreatingShowService by inject<CreatingShowService>()
    val gettingShow: GettingShow by inject<GettingShow>()

    route("$BASE_ROUTE/v1/shows") {
        post<CreateShowRequest> {
            val validationResult = it.checkIsValid()
            if (!validationResult.isValid) {
                call.respond(HttpStatusCode.BadRequest, validationResult)
                return@post
            }
            val show = createShow.create(it.toCommand())
            if (show != null) {
                call.respond(HttpStatusCode.Created, CreateShowResponse.fromShow(show))
            } else {
                call.respond("Error")
            }
        }

        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"]) ?: return@get call.respond(HttpStatusCode.BadRequest)
            val show = gettingShow.get(id)
            call.respond(
                HttpStatusCode.OK,
                GetShowResponse.fromShow(show)
            )
        }


    }


}