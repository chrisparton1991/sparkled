package io.sparkled.udpserver.impl.command

import io.sparkled.music.PlaybackState
import io.sparkled.udpserver.impl.subscriber.UdpClientSubscribers
import io.sparkled.udpserver.impl.subscriber.UdpClientSubscription
import java.net.InetAddress

/**
 * Notifies the server that a client is interested in receiving data for a stage prop. No response is returned to
 * the client.
 * Command syntax: S:StagePropCode:ClientID, e.g. S:P1:1.
 */
class SubscribeCommand(private val subscribers: UdpClientSubscribers) : UdpCommand {

    override fun handle(
        ipAddress: InetAddress,
        port: Int,
        args: List<String>,
        globalBrightness: Int,
        playbackState: PlaybackState
    ): ByteArray? {
        // TODO ditch clientId concept as stage props can now be grouped.
        val stagePropCode = args[1]
        val clientId = args[2].toInt()

        val ipSubscriptions = subscribers.getOrPut(ipAddress, { arrayListOf() })
        val clientIdSubscription = ipSubscriptions.firstOrNull { it.stagePropCode == stagePropCode }

        if (clientIdSubscription != null) {
            clientIdSubscription.clientId = clientId
            clientIdSubscription.timestamp = System.currentTimeMillis()
        } else {
            ipSubscriptions.add(
                UdpClientSubscription(stagePropCode, clientId, System.currentTimeMillis(), port)
            )
        }
        return null
    }

    companion object {
        const val KEY = "S"
    }
}
