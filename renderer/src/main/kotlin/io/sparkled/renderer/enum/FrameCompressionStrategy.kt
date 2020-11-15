package io.sparkled.renderer.enum

import io.sparkled.model.render.Led
import io.sparkled.model.render.RenderedFrame

enum class FrameCompressionStrategy(
    val id: Byte,
    val isEligible: (frame: RenderedFrame, uniqueColors: Set<String>) -> Boolean,
    val frameSize: (frame: RenderedFrame, uniqueColors: Set<String>) -> Int
) {
    NONE(
        0b0000,
        { _, _ -> true },
        { frame, _ -> 1 + frame.ledCount * Led.BYTES_PER_LED}
    ),
    SINGLE_COLOR(
        0b0001,
        { _, uniqueColors -> uniqueColors.size == 1 },
        { _, _ -> 1 + Led.BYTES_PER_LED}
    )
}