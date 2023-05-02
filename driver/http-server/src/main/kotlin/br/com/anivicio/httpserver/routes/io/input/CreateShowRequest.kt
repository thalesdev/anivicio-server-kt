package br.com.anivicio.httpserver.routes.io.input

import br.com.anivicio.domain.show.entity.ShowCastingRole
import br.com.anivicio.domain.show.entity.ShowGenre
import br.com.anivicio.domain.show.entity.ShowType
import br.com.anivicio.domain.show.ports.driver.Casting
import br.com.anivicio.domain.show.ports.driver.CreatingShowServiceCommand
import br.com.anivicio.httpserver.infra.Validation
import br.com.anivicio.shared.util.serializers.UUIDSerializer
import br.com.anivicio.shared.util.serializers.encoding.KJsonContentPolymorphicSerializer
import br.com.anivicio.shared.util.serializers.encoding.WrapperContentSerializer
import io.ktor.resources.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.valiktor.functions.hasSize
import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.time.LocalDate
import java.util.*
import br.com.anivicio.domain.show.ports.driver.ExistingCasting as DomainExistingCasting
import br.com.anivicio.domain.show.ports.driver.NewCasting as DomainNewCasting


object CastingDataSerializer : WrapperContentSerializer<CastingData>(KJsonContentPolymorphicSerializer.create())

sealed interface CastingData {
    val role: ShowCastingRole
    fun toDomain(): Casting
}

@Serializable
data class ExistingCasting(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    override val role: ShowCastingRole
) : CastingData {
    override fun toDomain(): Casting {
        return DomainExistingCasting(id, role)
    }
}

@Serializable
data class NewCasting(
    val name: String,
    override val role: ShowCastingRole,
    @Serializable(with = UUIDSerializer::class)
    val imageId: UUID
) : CastingData {
    override fun toDomain(): Casting {
        return DomainNewCasting(name, role, imageId)
    }
}


@Serializable
@Resource("/shows")
data class CreateShowRequest(
    val name: String,
    val description: String,
    val showType: ShowType,
    val genres: List<ShowGenre>,
    @Contextual
    val releaseDate: LocalDate,
    val mediaIds: List<@Contextual UUID>,
    val castingPerformers: List<@Serializable(with = CastingDataSerializer::class) CastingData>
) : Validation {
    fun toCommand(): CreatingShowServiceCommand {
        return CreatingShowServiceCommand(
            name,
            description,
            showType,
            genres,
            releaseDate,
            mediaIds,
            castingPerformers.map { it.toDomain() }
        )
    }

    override fun valid() {
        validate(this) {
            validate(CreateShowRequest::name).hasSize(min = 3, max = 255)
            validate(CreateShowRequest::description).hasSize(min = 3, max = 500)
            validate(CreateShowRequest::showType).isNotNull()
            validate(CreateShowRequest::genres).hasSize(min = 1)
            validate(CreateShowRequest::releaseDate).isGreaterThan(LocalDate.now().minusYears(100))
        }
    }
}

