package io.sparkled.renderer.enum

enum class CompressionLevel {
    /**
     * [FrameCompressionStrategy.NONE] will always be used.
     */
    NONE,

    /**
     * Ensure that each frame is represented in its most compact form.
     */
    FULL
}