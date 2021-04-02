package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.util.IdUtils

@Entity(name = "song_audio", idField = "songId")
data class SongAudioEntity(
    val songId: Int = IdUtils.NO_ID,
    val audioData: ByteArray = byteArrayOf(),
)
