package io.sparkled.viewmodel.stage

import io.sparkled.model.entity.v2.StageEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import io.sparkled.viewmodel.exception.ViewModelConversionException
import javax.inject.Singleton

@Singleton
class StageViewModelConverterImpl(
    private val db: DbService
) : StageViewModelConverter() {

    override fun toViewModel(model: StageEntity): StageViewModel {
        return StageViewModel()
            .setId(model.id)
            .setName(model.name)
            .setWidth(model.width)
            .setHeight(model.height)
    }

    override fun toModel(viewModel: StageViewModel): StageEntity {
        val stageId = viewModel.getId()
        val model = getStage(stageId)

        return model.copy(
            name = viewModel.getName() ?: "",
            width = viewModel.getWidth() ?: 0,
            height = viewModel.getHeight() ?: 0
        )
    }

    private fun getStage(stageId: Int?): StageEntity {
        return db.getById(stageId) ?: throw ViewModelConversionException("Stage with ID of '$stageId' not found.")
    }
}
