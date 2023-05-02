package br.com.anivicio.httpserver.plugins

import br.com.anivicio.shared.util.serializers.LocalDateSerializer
import br.com.anivicio.shared.util.serializers.LocalDateTimeSerializer
import br.com.anivicio.shared.util.serializers.UUIDSerializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(UUID::class, UUIDSerializer())
                contextual(LocalDateTime::class, LocalDateTimeSerializer())
                contextual(LocalDate::class, LocalDateSerializer())
            }
        })
    }
}
