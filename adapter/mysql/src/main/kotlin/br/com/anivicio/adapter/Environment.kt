package br.com.anivicio.adapter

import br.com.anivicio.adapter.infra.ExposedTransaction
import br.com.anivicio.adapter.media.repository.MediaItemRepository
import br.com.anivicio.adapter.show.repository.*
import br.com.anivicio.adapter.user.repository.UserRepository
import br.com.anivicio.domain.media.ports.driven.FindingMediaItem
import br.com.anivicio.domain.shared.Transaction
import br.com.anivicio.domain.show.ports.driven.*
import br.com.anivicio.domain.user.ports.driven.FindingUser
import br.com.anivicio.domain.user.ports.driven.InsertingUser
import org.koin.dsl.module

val adapterMysqlEnvironment = module {
    single<Transaction> { ExposedTransaction() }

    single<InsertingUser> { UserRepository() }
    single<FindingUser> { UserRepository() }

    single<FindingMediaItem> { MediaItemRepository() }

    single<FindingShowCasting> { ShowCastingRepository() }
    single<InsertingShowCasting> { ShowCastingRepository() }

    single<InsertingShowCastingPerformer> { ShowCastingPerformerRepository() }
    single<InsertingShowMedia> { ShowMediaRepository() }
    single<InsertingShowGenre> { ShowGenreRepository() }
    single<FindingShowGenre> { ShowGenreRepository() }
    single<InsertingShow> { ShowRepository() }
    single<FindingShow> { ShowRepository() }
}