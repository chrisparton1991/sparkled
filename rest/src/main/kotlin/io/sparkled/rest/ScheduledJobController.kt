package io.sparkled.rest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.spring.tx.annotation.Transactional
import io.sparkled.model.entity.v2.ScheduledJobEntity
import io.sparkled.persistence.*
import io.sparkled.rest.response.IdResponse
import io.sparkled.scheduler.SchedulerService
import io.sparkled.viewmodel.ViewModelConverterService
import io.sparkled.viewmodel.scheduledjob.ScheduledJobViewModel

@Controller("/api/scheduledJobs")
open class ScheduledJobController(
    private val db: DbService,
    private val schedulerService: SchedulerService,
    private val vm: ViewModelConverterService
) {

    @Get("/")
    @Transactional(readOnly = true)
    open fun getAllScheduledJobs(): HttpResponse<Any> {
        val scheduledJobs = db.getAll<ScheduledJobEntity>(orderBy = "id")
        return HttpResponse.ok(vm.scheduledJobSearch.toViewModels(scheduledJobs))
    }

    @Get("/{id}")
    @Transactional(readOnly = true)
    open fun getScheduledJob(id: Int): HttpResponse<Any> {
        val scheduledJob = db.getById<ScheduledJobEntity>(id)

        if (scheduledJob != null) {
            val viewModel = vm.scheduledJob.toViewModel(scheduledJob)
            return HttpResponse.ok(viewModel)
        }

        return HttpResponse.notFound("Scheduled job not found.")
    }

    @Post("/")
    @Transactional
    open fun createScheduledJob(scheduledJobViewModel: ScheduledJobViewModel): HttpResponse<Any> {
        val scheduledJob = vm.scheduledJob.toModel(scheduledJobViewModel)
        val scheduledJobId = db.insert(scheduledJob)
        schedulerService.reload()
        return HttpResponse.ok(IdResponse(scheduledJobId.toInt()))
    }

    @Delete("/{id}")
    @Transactional
    open fun deleteScheduledJob(id: Int): HttpResponse<Any> {
        val scheduledJob = db.getById<ScheduledJobEntity>(id)
        scheduledJob?.let { db.delete(it) }

        return HttpResponse.ok()
    }
}
