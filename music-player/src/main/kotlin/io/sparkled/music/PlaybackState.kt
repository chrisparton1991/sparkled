package io.sparkled.music

import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.model.entity.v2.SongAudioEntity
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.model.entity.v2.StagePropEntity
import io.sparkled.model.render.RenderedStagePropDataMap

/**
 * A container object holding all of the information pertaining to the current state of playback, in terms of audio
 * playback and associated rendered data for streaming to clients.
 */
data class PlaybackState(
        val sequences: List<SequenceEntity>? = null,
        val sequenceIndex: Int = 0,
        val repeat: Boolean = true,
        private val progressFunction: () -> Double = { 0.0 },
        val sequence: SequenceEntity? = null,
        val song: SongEntity? = null,
        val songAudio: SongAudioEntity? = null,
        val renderedStageProps: RenderedStagePropDataMap? = null,
        val stageProps: Map<String, StagePropEntity> = emptyMap()
) {

    val isEmpty: Boolean
        get() = sequences == null || sequence == null || song == null || songAudio == null || renderedStageProps == null

    val progress: Double
        get() = progressFunction.invoke()
}
