package io.sparkled.renderer.effect

import io.sparkled.model.animation.easing.Easing
import io.sparkled.model.animation.easing.EasingTypeCode
import io.sparkled.model.animation.effect.Effect
import io.sparkled.model.animation.effect.EffectTypeCode
import io.sparkled.model.animation.fill.BlendMode
import io.sparkled.model.animation.fill.Fill
import io.sparkled.model.animation.fill.FillTypeCode
import io.sparkled.model.animation.param.ParamCode
import io.sparkled.model.render.RenderedFrame
import io.sparkled.model.render.RenderedStagePropData
import io.sparkled.model.util.ArgumentUtils.arg
import io.sparkled.renderer.enum.CompressionLevel
import io.sparkled.renderer.enum.FrameCompressionStrategy
import io.sparkled.util.RenderUtils
import io.sparkled.util.matchers.SparkledMatchers.hasRenderedFrames
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer
import org.hamcrest.Matchers.`is` as eq
import kotlin.intArrayOf as f

class FlashEffectRendererTest {

    @Test
    fun can_render() {
        val effect = Effect(
            endFrame = 10,
            type = EffectTypeCode.FLASH,
            easing = Easing(EasingTypeCode.LINEAR),
            fill = Fill(
                FillTypeCode.SOLID,
                BlendMode.NORMAL,
                mapOf(
                    arg(ParamCode.COLOR, "#ffffff")
                )
            )
        )

        val renderedStagePropData = RenderUtils.render(effect, effect.endFrame + 1, 10)

        assertThat(
            renderedStagePropData, hasRenderedFrames(
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000),
                f(0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333),
                f(0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666),
                f(0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999),
                f(0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC),
                f(0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF),
                f(0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC, 0xCCCCCC),
                f(0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999, 0x999999),
                f(0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666, 0x666666),
                f(0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333, 0x333333),
                f(0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000)
            )
        )

        val optimisedData = optimise(renderedStagePropData, CompressionLevel.FULL)
        assertThat(
            optimisedData, eq(
                byteArrayOf(
                    // Total frame count
                    0, 0, 0, 11,

                    // Frame offsets
                    0, 0, 0, 48,
                    0, 0, 0, 52,
                    0, 0, 0, 56,
                    0, 0, 0, 60,
                    0, 0, 0, 64,
                    0, 0, 0, 68,
                    0, 0, 0, 72,
                    0, 0, 0, 76,
                    0, 0, 0, 80,
                    0, 0, 0, 84,
                    0, 0, 0, 88,

                    // LED data.
                    0b001, u(0x00), u(0x00), u(0x00),
                    0b001, u(0x33), u(0x33), u(0x33),
                    0b001, u(0x66), u(0x66), u(0x66),
                    0b001, u(0x99), u(0x99), u(0x99),
                    0b001, u(0xCC), u(0xCC), u(0xCC),
                    0b001, u(0xFF), u(0xFF), u(0xFF),
                    0b001, u(0xCC), u(0xCC), u(0xCC),
                    0b001, u(0x99), u(0x99), u(0x99),
                    0b001, u(0x66), u(0x66), u(0x66),
                    0b001, u(0x33), u(0x33), u(0x33),
                    0b001, u(0x00), u(0x00), u(0x00),
                )
            )
        )
    }

    private fun u(byte: Int): Byte = (byte and 0xFF).toByte()

    private val BYTES_PER_INT = 4

    data class CompressedFramePlan(
        val frame: RenderedFrame,
        val strategy: FrameCompressionStrategy,
        val uniqueColors: Set<String>,
        val compressedSize: Int
    )

    private fun optimise(data: RenderedStagePropData, compressionLevel: CompressionLevel): ByteArray {
        // one integer per frame to denote offsets, and an additional integer to indicate the header size itself
        val frameCountMarkerSize = 1 * BYTES_PER_INT
        val framePositionTableSize = data.frameCount * BYTES_PER_INT

        val frames = (data.startFrame..data.endFrame).map {
            RenderedFrame(data.startFrame, it, data.ledCount, data.data)
        }

        val plannedFrames = frames.map {
            val uniqueColors = uniqueColorsInFrame(it)
            val strategy = if (compressionLevel == CompressionLevel.NONE) FrameCompressionStrategy.NONE else {
                FrameCompressionStrategy.values()
                    .filter { fcs -> fcs.isEligible(it, uniqueColors) }
                    .minByOrNull { fcs -> fcs.frameSize(it, uniqueColors) } ?: FrameCompressionStrategy.NONE
            }

            CompressedFramePlan(it, strategy, uniqueColors, strategy.frameSize(it, uniqueColors))
        }

        val optimisedDataSize = frameCountMarkerSize + framePositionTableSize + plannedFrames.sumBy { it.compressedSize }
        val optimisedData = ByteArray(optimisedDataSize)

        // Set the first four bytes to the size of the header.
        val frameCountMarker = intToBytes(frames.size)
        frameCountMarker.copyInto(optimisedData)

        var nextInsertionIndex = frameCountMarkerSize + framePositionTableSize

        plannedFrames.forEachIndexed { i, plannedFrame ->
            val (frame, strategy, uniqueColors) = plannedFrame
            val frameOffset = nextInsertionIndex
            val frameOffsetBytes = intToBytes(frameOffset)
            System.arraycopy(frameOffsetBytes, 0, optimisedData, frameCountMarkerSize + i * BYTES_PER_INT, BYTES_PER_INT)
            nextInsertionIndex += strategy.frameSize(frame, uniqueColors)

            optimisedData[frameOffset] = strategy.id
            when (strategy) {
                FrameCompressionStrategy.NONE -> {
                    val unoptimisedFrameData = frame.getData()
                    System.arraycopy(unoptimisedFrameData, 0, optimisedData, frameOffset, unoptimisedFrameData.size)
                }
                FrameCompressionStrategy.SINGLE_COLOR -> {
                    optimisedData[frameOffset + 1] = u(frame.getLed(0).r)
                    optimisedData[frameOffset + 2] = u(frame.getLed(0).g)
                    optimisedData[frameOffset + 3] = u(frame.getLed(0).b)
                }
            }
        }

        return optimisedData
    }

    private fun uniqueColorsInFrame(frame: RenderedFrame): Set<String> {
        return (0 until frame.ledCount)
            .map { frame.getLed(it).toString() }
            .toSet()
    }

    private fun intToBytes(int: Int) = ByteBuffer.allocate(4).putInt(int).array()
}
