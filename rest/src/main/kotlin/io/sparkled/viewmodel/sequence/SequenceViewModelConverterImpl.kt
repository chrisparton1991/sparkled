package io.sparkled.viewmodel.sequence

import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.model.util.SequenceUtils
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import io.sparkled.persistence.v2.query.song.GetSongBySequenceIdQuery
import javax.inject.Singleton

@Singleton
class SequenceViewModelConverterImpl(
    private val db: DbService,
) : SequenceViewModelConverter() {

    override fun toViewModel(model: SequenceEntity): SequenceViewModel {
        return SequenceViewModel()
            .setId(model.id)
            .setSongId(model.songId)
            .setStageId(model.stageId)
            .setName(model.name)
            .setFramesPerSecond(model.framesPerSecond)
            .setFrameCount(getFrameCount(model))
            .setStatus(model.status)
    }

    private fun getFrameCount(sequence: SequenceEntity): Int {
        val song = db.query(GetSongBySequenceIdQuery(sequence.id))
        return if (song == null) 0 else SequenceUtils.getFrameCount(song, sequence)
    }

    override fun toModel(viewModel: SequenceViewModel): SequenceEntity {
        val model = db.getById(viewModel.getId()) ?: SequenceEntity()

        return model.copy(
            songId = viewModel.getSongId()!!,
            stageId = viewModel.getStageId()!!,
            name = viewModel.getName()!!,
            framesPerSecond = viewModel.getFramesPerSecond()!!,
            status = viewModel.getStatus()!!,
        )
    }
}
