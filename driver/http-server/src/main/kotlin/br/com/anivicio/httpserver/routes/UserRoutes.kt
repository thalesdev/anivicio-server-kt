package br.com.anivicio.httpserver.routes

import br.com.anivicio.domain.user.ports.driver.CreatingUser
import br.com.anivicio.httpserver.routes.io.input.CreateUserRequest
import br.com.anivicio.httpserver.routes.io.output.CreateUserResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.userRoutes() {

    val createUser: CreatingUser by inject<CreatingUser>()

    route("$BASE_ROUTE/v1/users") {
        post<CreateUserRequest> {
            val validationResult = it.checkIsValid()
            if (!validationResult.isValid) {
                call.respond(HttpStatusCode.BadRequest, validationResult)
                return@post
            }
            val user = createUser.create(it.toCommand())
            if (user != null) {
                call.respond(HttpStatusCode.Created, CreateUserResponse.fromUser(user))
            } else {
                call.respond("Error")
            }
        }
    }

}