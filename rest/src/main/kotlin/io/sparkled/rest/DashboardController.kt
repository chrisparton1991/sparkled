package io.sparkled.rest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.spring.tx.annotation.Transactional
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getAll
import io.sparkled.viewmodel.ViewModelConverterService
import io.sparkled.viewmodel.dashboard.DashboardViewModel

@Controller("/api/dashboard")
open class DashboardController(
    private val db: DbService,
    private val vm: ViewModelConverterService
) {

    @Get("/")
    @Transactional(readOnly = true)
    open fun getDashboard(): HttpResponse<Any> {
        val dashboard = DashboardViewModel(
            stages = vm.stageSearch.toViewModels(db.getAll(orderBy = "name")),
            songs = db.getAll<SongEntity>(orderBy = "name").map { vm.song.toViewModel(it) },
            sequences = vm.sequenceSearch.toViewModels(db.getAll(orderBy = "name")),
            playlists = vm.playlistSearch.toViewModels(db.getAll(orderBy = "name")),
            scheduledTasks = vm.scheduledJobSearch.toViewModels(db.getAll(orderBy = "id")),
        )

        return HttpResponse.ok(dashboard)
    }
}
