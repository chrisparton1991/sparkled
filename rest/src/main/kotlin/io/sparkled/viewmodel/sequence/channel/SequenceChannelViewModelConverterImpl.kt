package io.sparkled.viewmodel.sequence.channel

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.animation.SequenceChannelEffects
import io.sparkled.model.entity.v2.SequenceChannelEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import javax.inject.Singleton

@Singleton
class SequenceChannelViewModelConverterImpl(
    private val db: DbService,
    private val objectMapper: ObjectMapper
) : SequenceChannelViewModelConverter() {

    override fun toViewModel(model: SequenceChannelEntity): SequenceChannelViewModel {
        val sequenceChannelEffects = objectMapper.readValue(model.channelJson, SequenceChannelEffects::class.java)

        return SequenceChannelViewModel()
            .setUuid(model.uuid)
            .setSequenceId(model.sequenceId)
            .setStagePropUuid(model.stagePropUuid)
            .setName(model.name)
            .setDisplayOrder(model.displayOrder)
            .setEffects(sequenceChannelEffects.effects)
    }

    override fun toModel(viewModel: SequenceChannelViewModel): SequenceChannelEntity {
        val model = db.getById<SequenceChannelEntity>(viewModel.getUuid()!!)
            ?.let { if (it.sequenceId == viewModel.getSequenceId()) it else null }
            ?: SequenceChannelEntity()

        val channelJson = objectMapper.writeValueAsString(SequenceChannelEffects(viewModel.getEffects()))
        return model.copy(
            uuid = viewModel.getUuid()!!,
            sequenceId = viewModel.getSequenceId()!!,
            stagePropUuid = viewModel.getStagePropUuid()!!,
            name = viewModel.getName()!!,
            displayOrder = viewModel.getDisplayOrder()!!,
            channelJson = channelJson
        )
    }
}
