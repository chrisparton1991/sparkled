package io.sparkled.model.render

import java.util.Arrays

class RenderedFrame(private val startFrame: Int, val frameNumber: Int, val ledCount: Int, private val data: ByteArray, private val dummyFrame: Boolean = false) {

    fun getData(): ByteArray {
        if (dummyFrame) {
            // Dummy frames should only ever appear in preview renders for stateful effects.
            throw RuntimeException("Attempted to retrieve data from dummy frame.")
        }

        val bytesPerFrame = ledCount * Led.BYTES_PER_LED
        val offset = (frameNumber - startFrame) * bytesPerFrame

        val frameData = ByteArray(bytesPerFrame)
        System.arraycopy(data, offset, frameData, 0, bytesPerFrame)

        return frameData
    }

    fun getLed(ledIndex: Int): Led {
        return if (dummyFrame) {
            Led(byteArrayOf(0, 0, 0), 0, 0)
        } else {
            val bytesPerFrame = ledCount * Led.BYTES_PER_LED
            val offset = (frameNumber - startFrame) * bytesPerFrame
            Led(data, ledIndex, offset)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RenderedFrame

        if (startFrame != other.startFrame) return false
        if (frameNumber != other.frameNumber) return false
        if (ledCount != other.ledCount) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startFrame
        result = 31 * result + frameNumber
        result = 31 * result + ledCount
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        val dataString = Arrays.toString(data)
        return "RenderedFrame(startFrame=$startFrame, frameNumber=$frameNumber, ledCount=$ledCount, data=$dataString)"
    }
}
