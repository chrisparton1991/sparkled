package io.sparkled.udpserver.impl.command

import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.model.entity.v2.SongAudioEntity
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.model.entity.v2.StagePropEntity
import io.sparkled.model.render.RenderedStagePropDataMap
import io.sparkled.music.PlaybackState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import java.net.InetAddress
import java.nio.charset.StandardCharsets

internal class GetStagePropCodesCommandTest {

    @Test
    fun can_retrieve_stage_prop_codes() {
        val command = GetStagePropCodesCommand()
        val response = command.handle(
            ipAddress = InetAddress.getLocalHost(),
            port = 2812,
            args = listOf(GetStagePropCodesCommand.KEY),
            globalBrightness = 0,
            playbackState = PlaybackState(
                sequences = emptyList(),
                sequenceIndex = 0,
                progressFunction = { 0.0 },
                sequence = SequenceEntity(),
                song = SongEntity(),
                songAudio = SongAudioEntity(),
                renderedStageProps = RenderedStagePropDataMap(),
                stageProps = mapOf(
                    "P1" to StagePropEntity(code = "P1", displayOrder = 1),
                    "P2" to StagePropEntity(code = "P2", displayOrder = 3),
                    "P3" to StagePropEntity(code = "P3", displayOrder = 2)
                )
            )
        )

        val responseString = String(response, StandardCharsets.UTF_8)
        assertThat(responseString, `is`("P1:P3:P2"))
    }
}
