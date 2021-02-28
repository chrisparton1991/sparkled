package io.sparkled.rest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.spring.tx.annotation.Transactional
import io.sparkled.model.entity.v2.StageEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getAll
import io.sparkled.persistence.getById
import io.sparkled.persistence.insert
import io.sparkled.persistence.stage.StagePersistenceService
import io.sparkled.persistence.v2.query.stage.GetStagePropsByStageIdQuery
import io.sparkled.rest.response.IdResponse
import io.sparkled.viewmodel.stage.StageViewModel
import io.sparkled.viewmodel.stage.StageViewModelConverter
import io.sparkled.viewmodel.stage.prop.StagePropViewModelConverter
import io.sparkled.viewmodel.stage.search.StageSearchViewModelConverter

@Controller("/api/stages")
open class StageController(
    private val db: DbService,
    private val stagePersistenceService: StagePersistenceService,
    private val stageSearchViewModelConverter: StageSearchViewModelConverter,
    private val stageViewModelConverter: StageViewModelConverter,
    private val stagePropViewModelConverter: StagePropViewModelConverter
) {

    @Get("/")
    @Transactional(readOnly = true)
    open fun getAllStages(): HttpResponse<Any> {
        val stages = db.getAll<StageEntity>(orderBy = "name")
        return HttpResponse.ok(stageSearchViewModelConverter.toViewModels(stages))
    }

    @Get("/{id}")
    @Transactional(readOnly = true)
    open fun getStage(id: Int): HttpResponse<Any> {
        val stage = db.getById<StageEntity>(id)

        if (stage != null) {
            val viewModel = stageViewModelConverter.toViewModel(stage)

            val stageProps = db.query(GetStagePropsByStageIdQuery(id))
                .map(stagePropViewModelConverter::toViewModel)
            viewModel.setStageProps(stageProps)

            return HttpResponse.ok(viewModel)
        }

        return HttpResponse.notFound("Stage not found.")
    }

    @Post("/")
    @Transactional
    open fun createStage(stageViewModel: StageViewModel): HttpResponse<Any> {
        val stage = stageViewModelConverter.toModel(stageViewModel)
        val stageId = db.insert(stage)
        return HttpResponse.ok(IdResponse(stageId.toInt()))
    }

    @Put("/{id}")
    @Transactional
    open fun updateStage(id: Int, stageViewModel: StageViewModel): HttpResponse<Any> {
        stageViewModel.setId(id) // Prevent client-side ID tampering.

        val stage = stageViewModelConverter.toModel(stageViewModel)
        val stageProps = stageViewModel.getStageProps()
            .map(stagePropViewModelConverter::toModel)
            .map { it.copy(stageId = id) }

        // TODO
//        stagePersistenceService.saveStage(stage, stageProps)
        return HttpResponse.ok()
    }

    @Delete("/{id}")
    @Transactional
    open fun deleteStage(id: Int): HttpResponse<Any> {
        stagePersistenceService.deleteStage(id)
        return HttpResponse.ok()
    }
}
