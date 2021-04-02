package io.sparkled.viewmodel.playlist.sequence

import io.sparkled.model.entity.v2.PlaylistSequenceEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import javax.inject.Singleton

@Singleton
class PlaylistSequenceViewModelConverterImpl(
    private val db: DbService
) : PlaylistSequenceViewModelConverter() {

    override fun toViewModel(model: PlaylistSequenceEntity): PlaylistSequenceViewModel {
        return PlaylistSequenceViewModel()
            .setUuid(model.uuid)
            .setSequenceId(model.sequenceId)
            .setDisplayOrder(model.displayOrder)
    }

    override fun toModel(viewModel: PlaylistSequenceViewModel): PlaylistSequenceEntity {
        val model = db.getById(viewModel.getSequenceId()) ?: PlaylistSequenceEntity()

        return model.copy(
            uuid = viewModel.getUuid()!!,
            sequenceId = viewModel.getSequenceId()!!,
            displayOrder = viewModel.getDisplayOrder()!!
        )
    }
}
