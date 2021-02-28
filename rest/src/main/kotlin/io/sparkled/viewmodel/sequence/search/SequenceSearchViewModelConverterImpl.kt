package io.sparkled.viewmodel.sequence.search

import io.sparkled.model.constant.ModelConstants.MS_PER_SECOND
import io.sparkled.model.entity.Song
import io.sparkled.model.entity.Stage
import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.model.entity.v2.StageEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getAll
import javax.inject.Singleton

@Singleton
class SequenceSearchViewModelConverterImpl(
    private val db: DbService
) : SequenceSearchViewModelConverter() {

    override fun toViewModels(models: Collection<SequenceEntity>): List<SequenceSearchViewModel> {
        val songs = db.getAll<SongEntity>().associateBy({ it.id }, { it })
        val stages = db.getAll<StageEntity>().associateBy({ it.id }, { it })
        return models.map { model -> toViewModel(model, songs, stages) }.toList()
    }

    private fun toViewModel(
        model: SequenceEntity,
        songs: Map<Int, SongEntity>,
        stages: Map<Int, StageEntity>
    ): SequenceSearchViewModel {
        val song = songs[model.songId]!!
        val stage = stages[model.stageId]!!

        return SequenceSearchViewModel(
            id = model.id,
            name = model.name,
            songName = song.name,
            stageName = stage.name,
            framesPerSecond = model.framesPerSecond,
            durationSeconds = song.durationMs.div(MS_PER_SECOND),
            status = model.status
        )
    }
}
