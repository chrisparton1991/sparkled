package io.sparkled.viewmodel.sequence.search

import io.sparkled.model.entity.SequenceStatus
import io.sparkled.viewmodel.ViewModel

data class SequenceSearchViewModel(
    val id: Int,
    val name: String,
    val songName: String,
    val stageName: String,
    val framesPerSecond: Int,
    val durationSeconds: Int,
    val status: SequenceStatus,
) : ViewModel
