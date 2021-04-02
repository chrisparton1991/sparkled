package io.sparkled.udpserver.impl.command

import io.sparkled.music.PlaybackState
import java.net.InetAddress

interface UdpCommand {

    fun handle(
        ipAddress: InetAddress,
        port: Int,
        args: List<String>,
        globalBrightness: Int,
        playbackState: PlaybackState
    ): ByteArray?
}
