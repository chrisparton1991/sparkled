package io.sparkled.viewmodel.stage.prop

import io.sparkled.model.entity.v2.StagePropEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import javax.inject.Singleton

@Singleton
class StagePropViewModelConverterImpl(
    private val db: DbService
) : StagePropViewModelConverter() {

    override fun toViewModel(model: StagePropEntity): StagePropViewModel {
        return StagePropViewModel()
            .setUuid(model.uuid)
            .setStageId(model.stageId)
            .setCode(model.code)
            .setName(model.name)
            .setType(model.type)
            .setLedCount(model.ledCount)
            .setReverse(model.reverse)
            .setPositionX(model.positionX)
            .setPositionY(model.positionY)
            .setScaleX(model.scaleX)
            .setScaleY(model.scaleY)
            .setRotation(model.rotation)
            .setBrightness(model.brightness)
            .setDisplayOrder(model.displayOrder)
    }

    override fun toModel(viewModel: StagePropViewModel): StagePropEntity {
        val stageId = viewModel.getStageId()!!
        val model = db.getById(stageId) ?: StagePropEntity(stageId = stageId)

        return model.copy(
            uuid = viewModel.getUuid()!!,
            stageId = viewModel.getStageId()!!,
            code = viewModel.getCode()!!,
            name = viewModel.getName()!!,
            type = viewModel.getType()!!,
            ledCount = viewModel.getLedCount()!!,
            reverse = viewModel.isReverse()!!,
            positionX = viewModel.getPositionX()!!,
            positionY = viewModel.getPositionY()!!,
            scaleX = viewModel.getScaleX()!!,
            scaleY = viewModel.getScaleY()!!,
            rotation = viewModel.getRotation()!!,
            brightness = viewModel.getBrightness()!!,
            displayOrder = viewModel.getDisplayOrder()!!,
        )
    }
}
