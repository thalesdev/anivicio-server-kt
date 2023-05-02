package br.com.anivicio.domain.show

import br.com.anivicio.domain.show.ports.driver.*
import br.com.anivicio.domain.show.services.CreateShowService
import br.com.anivicio.domain.show.usecases.CreateShow
import br.com.anivicio.domain.show.usecases.CreateShowCasting
import br.com.anivicio.domain.show.usecases.CreateShowMedia
import br.com.anivicio.domain.show.usecases.GetShow
import org.koin.dsl.module

val showEnvironment = module {
    single<CreatingShow> { CreateShow(get()) }
    single<CreatingShowCasting> { CreateShowCasting(get(), get()) }
    single<CreatingShowMedia> { CreateShowMedia(get()) }
    single<CreatingShowService> { CreateShowService(get(), get(), get(), get(), get(), get(), get()) }
    single<GettingShow> { GetShow(get(), get(), get(), get(), get()) }
}